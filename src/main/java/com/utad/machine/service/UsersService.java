package com.utad.machine.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.utad.machine.dto.AdminUserDto;
import com.utad.machine.dto.ResetUserDto;
import com.utad.machine.dto.UserDto;
import com.utad.machine.entity.SecuredAccountsEntity;
import com.utad.machine.entity.UserEntity;
import com.utad.machine.exception.BusinessLogicException;
import com.utad.machine.mapper.UsersMapper;
import com.utad.machine.repository.SecuredAccountsRepository;
import com.utad.machine.repository.UsersRepository;

@Service
@Transactional
public class UsersService {

	@Autowired
	private SecuredAccountsRepository securedAccountsRepository;

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private UsersMapper usersMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private Environment env;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private HttpServletRequest request;

	@Value("${google.recaptcha.secret}")
	String recaptchaSecret;

	@Autowired
	RestTemplateBuilder restTemplateBuilder;

	public UserDto getByUsername(String username) {

		UserEntity userEntity = usersRepository.findByUsername(username);
		UserDto userDto = usersMapper.toDto(userEntity);
		return userDto;

	}

	public UserDto getByUserId(Long iduser) {

		UserEntity userEntity = usersRepository.findByUserId(iduser);
		UserDto userDto = usersMapper.toDto(userEntity);
		return userDto;

	}

	public UserDto getByToken(String token) {

		UserEntity userEntity = usersRepository.findByToken(token);
		UserDto userDto = usersMapper.toDto(userEntity);
		return userDto;

	}

	public List<AdminUserDto> getAll() {

		List<UserEntity> userEntities = usersRepository.findAll();
		List<AdminUserDto> userDtos = usersMapper.toAdminDto(userEntities);

		return userDtos;

	}

	private String getCurrentTimeUsingCalendar() {
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDate = dateFormat.format(date);
		return formattedDate;
	}

	public Long getDiferencia(String fechaIni, String fechaFin) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date fechaInicial;
		Date fechaFinal;
		long diferencia = 0;

		try {
			fechaInicial = dateFormat.parse(fechaIni);
			fechaFinal = dateFormat.parse(fechaFin);
			diferencia = fechaInicial.getTime() - fechaFinal.getTime();
		} catch (Exception e) {
			throw new BusinessLogicException("error-occurred");
		}

		return diferencia;
	}

	public UserDto create(String username, String password1, String password2, String role) {

		if (!password1.equals(password2)) {
			throw new BusinessLogicException("incorrect-password");
		}

		UserDto userDtoAux = getByUsername(username);

		if (userDtoAux != null) {
			throw new BusinessLogicException("user-already-exist");
		}

		UserEntity userEntity = new UserEntity();
		userEntity.setUsername(username);
		userEntity.setPassword(passwordEncoder.encode(password1));
		userEntity.setRole(role);
		userEntity.setToken(null);
		userEntity.setCreatedOn(getCurrentTimeUsingCalendar());
		userEntity.setLastToken(null);
		userEntity.setUserSecuredAccounts(null);
		userEntity = usersRepository.save(userEntity);

		UserDto userDto = usersMapper.toDto(userEntity);

		return userDto;

	}

	public void modify(Long idUser, String oldPassword, String newPassword1, String newPassword2) {

		UserEntity userEntity = new UserEntity();

		UserDto userDto = getByUserId(idUser);

		if (!passwordEncoder.matches(oldPassword, userDto.getPassword())) {
			throw new BusinessLogicException("incorrect-password");
		}
		if (!newPassword1.equals(newPassword2)) {
			throw new BusinessLogicException("incorrect-password");
		}

		userEntity.setUserId(idUser);
		userEntity.setUsername(userDto.getUsername());
		userEntity.setPassword(passwordEncoder.encode(newPassword1));
		userEntity.setRole(userDto.getRole());
		userEntity.setToken(userDto.getToken());
		userEntity.setCreatedOn(userDto.getCreatedOn());
		userEntity.setLastToken(userDto.getLastToken());

		List<SecuredAccountsEntity> securedAccountEntities = securedAccountsRepository
				.findByUserUserIdOrderByName(idUser);

		userEntity.setUserSecuredAccounts(securedAccountEntities);

		userEntity = usersRepository.save(userEntity);
	}

	public void adminModify(Long idAdmin, Long idUser, String adminPassword, String newPassword1, String newPassword2) {

		UserEntity userEntity = new UserEntity();

		UserDto adminDto = getByUserId(idAdmin);
		UserDto userDto = getByUserId(idUser);

		if (!passwordEncoder.matches(adminPassword, adminDto.getPassword())) {
			throw new BusinessLogicException("incorrect-password");
		}
		if (!newPassword1.equals(newPassword2)) {
			throw new BusinessLogicException("incorrect-password");
		}

		userEntity.setUserId(idUser);
		userEntity.setUsername(userDto.getUsername());
		userEntity.setPassword(passwordEncoder.encode(newPassword1));
		userEntity.setRole(userDto.getRole());
		userEntity.setToken(userDto.getToken());
		userEntity.setCreatedOn(userDto.getCreatedOn());
		userEntity.setLastToken(userDto.getLastToken());

		List<SecuredAccountsEntity> securedAccountEntities = securedAccountsRepository
				.findByUserUserIdOrderByName(idUser);

		userEntity.setUserSecuredAccounts(securedAccountEntities);

		userEntity = usersRepository.save(userEntity);
	}

	public void deleteByUserId(Long userId) {

		UserEntity userEntity = usersRepository.findByUserId(userId);

		if (userEntity == null) {
			throw new BusinessLogicException("user-does-not-exist");
		}

		usersRepository.delete(userEntity);

	}

	@Async
	public void sendEmail(SimpleMailMessage email) {
		mailSender.send(email);
	}

	public void rememberPassword(String userName) {

		UserDto userDtoAux = getByUsername(userName);

		if (userDtoAux == null) {
			throw new BusinessLogicException("user-does-not-exist");
		}

		// Generate random 36-character string token for reset password
		userDtoAux.setToken(UUID.randomUUID().toString());

		UserEntity userEntity = new UserEntity();
		userEntity.setUserId(userDtoAux.getUserId());
		userEntity.setUsername(userDtoAux.getUsername());
		userEntity.setPassword(userDtoAux.getPassword());
		userEntity.setRole(userDtoAux.getRole());
		userEntity.setToken(userDtoAux.getToken());
		userEntity.setCreatedOn(userDtoAux.getCreatedOn());
		userEntity.setLastToken(getCurrentTimeUsingCalendar());

		List<SecuredAccountsEntity> securedAccountEntities = securedAccountsRepository
				.findByUserUserIdOrderByName(userDtoAux.getUserId());

		userEntity.setUserSecuredAccounts(securedAccountEntities);

		userEntity = usersRepository.save(userEntity);

		// Email message
		String appUrl = request.getScheme() + "://" + request.getServerName();

		SimpleMailMessage passwordResetEmail = new SimpleMailMessage();

		String mail = env.getProperty("spring.mail.username");

		passwordResetEmail.setFrom(mail);
		passwordResetEmail.setTo(userEntity.getUsername());
		passwordResetEmail.setSubject("Secured Machine - Reinicio Password");
		passwordResetEmail.setText("Para reiniciar la password haga click en el siguiente enlace:\n" + appUrl
				+ "/#/reset?token=" + userEntity.getToken());

		sendEmail(passwordResetEmail);

	}

	public void resetUser(ResetUserDto resetUserDto) {

		UserDto userDto = getByToken(resetUserDto.getToken());

		if (userDto == null) {
			throw new BusinessLogicException("user-does-not-exist");
		}

		Long limite = new Long(3600000);

		if (env.getProperty("caducidad.token") != null) {
			limite = new Long(env.getProperty("caducidad.token"));
		}

		Long diferencia = getDiferencia(getCurrentTimeUsingCalendar(), userDto.getLastToken());

		if (diferencia > limite) {
			throw new BusinessLogicException("token-time-limit-exceeded");
		}

		UserEntity userEntity = new UserEntity();

		if (!resetUserDto.getNewPassword1().equals(resetUserDto.getNewPassword2())) {
			throw new BusinessLogicException("incorrect-password");
		}

		userEntity.setUserId(userDto.getUserId());
		userEntity.setUsername(userDto.getUsername());
		userEntity.setPassword(passwordEncoder.encode(resetUserDto.getNewPassword1()));
		userEntity.setRole(userDto.getRole());
		userEntity.setToken(null);
		userEntity.setCreatedOn(userDto.getCreatedOn());
		userEntity.setLastToken(null);

		List<SecuredAccountsEntity> securedAccountEntities = securedAccountsRepository
				.findByUserUserIdOrderByName(userDto.getUserId());

		userEntity.setUserSecuredAccounts(securedAccountEntities);

		userEntity = usersRepository.save(userEntity);
	}

}
