import Vue from 'vue'
import Vuetify from 'vuetify'
import '@babel/polyfill'
import 'api/resource'
import router from 'router/router'
import App from 'pages/App.vue'
import store from 'store/store'
import { connect } from './util/ws'
import 'vuetify/dist/vuetify.min.css'
import * as Sentry from "@sentry/browser";
import { Vue as VueIntegration } from "@sentry/integrations";
import { Integrations } from '@sentry/tracing';

Sentry.init({
    dsn: "https://56077f6d1beb48729686c2e88d7ec061@o445739.ingest.sentry.io/5422696",
    integrations: [
        new VueIntegration({
            Vue,
            tracing: true
        }),
        new Integrations.BrowserTracing()
    ],
    tracesSampleRate: 1
});

Sentry.configureScope(scope =>
    scope.setUser({
        id: profile && profile.id,
        username: profile && profile.name
    })
)

if (profile) {
    connect()
}

Vue.use(Vuetify)

new Vue({
    el: '#app',
    vuetify: new Vuetify(),
    store,
    router,
    render: a => a(App)
})