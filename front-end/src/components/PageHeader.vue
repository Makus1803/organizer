<template>
  <div class="logo-wrapper">
    <img class="logo" src="/static/images/logo.png">
    <h1>Organizer</h1>
    <div class="tagline">Aplikacja do zarządzania parkingiem</div>
  </div>
</template>

<script>
export default {
  name: 'Logo'
}
</script>

<style lang="scss" scoped>
  .logo-wrapper{
    text-align: center;
    margin-bottom: 40px;
    .tagline{
      line-height: 180%;
      color: #666;
    }
  }
  .logo{
    max-width: 150px;
    margin: 0 auto;
  }
</style>
