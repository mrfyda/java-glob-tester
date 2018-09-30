<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
      <div id="example-1">
          <button v-on:click="refreshMessage()">test</button>
          <p>Result: {{ message }}</p>
      </div>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';

@Component
export default class HelloWorld extends Vue {
  @Prop() private msg!: string;

  public refreshMessage() {
      this.$store.dispatch('refreshMessage').then(() => {
      // do stuff
    });
  }

  get message() { return this.$store.state.message; }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
