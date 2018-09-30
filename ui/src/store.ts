import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    message: '',
  },
  mutations: {
    updateMessage(state, payload) {
      state.message = payload;
    },
  },
  actions: {
    refreshMessage(context) {
      fetch('http://localhost:9000/api/v1', {
        method: 'POST',
        mode: 'cors',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({pattern: '/app/**', path: '/app/Controller.scala'}),
      }).then((res) => res.json() as Promise<boolean>)
        .then((data) => context.commit('updateMessage', data));
    },
  },
});
