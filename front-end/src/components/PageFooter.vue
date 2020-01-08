<template>
  <footer class="footer">
    <span class="copyright"></span>
    <ul class="footer-links list-inline float-right">
      <li class="list-inline-item"></li>
      <li class="list-inline-item"></li>
      <li class="list-inline-item"></li>
      <li class="list-inline-item"></li>
    </ul>
  </footer>
</template>

<script>
export default {
  name: 'PageFooter'
}
</script>

<style lang="scss" scoped>
  .footer{
    width: 100%;
    font-size: 13px;
    color: #666;
    line-height: 40px;
    border-top: 1px solid #ddd;
    margin-top: 50px;

    .list-inline-item{
     margin-right: 10px;
    }

    a{
      color: #666;
    }
  }
</style>
