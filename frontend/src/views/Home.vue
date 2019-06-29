<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
    <animation-lister></animation-lister>
      <router-link to="editor">editor</router-link>
  </div>
</template>

<script>

import AnimationLister from '../components/AnimationLister'

export default {
  name: 'home',
  components: {
    AnimationLister
  }
}
</script>
