import i18next from 'i18next';
import translation_es from 'i18n/translations/es.json'

i18next.init({
    lng: 'es',
    resources: {
        es: { translation: translation_es }
    },
    interpolation: {
        escapeValue: false
    }
});
