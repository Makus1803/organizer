<template>
<div class="container">
  <div class="row justify-content-center">
    <div class="register-form">
      <div class="logo-wrapper">
        <img class="logo" src="/static/images/logo.png">
        <div class="tagline">Open source task management tool</div>
      </div>
      <form @submit.prevent="submitForm">
        <div v-show="errorMessage" class="alert alert-danger failed">{{ errorMessage }}</div>
        <div class="form-group">
          <label for="username">Username</label>
          <input type="text" class="form-control" id="username" v-model="form.username">
          <div class="field-error" v-if="$v.form.username.$dirty">
            <div class="error" v-if="!$v.form.username.required">Username is required</div>
            <div class="error" v-if="!$v.form.username.alphaNum">Username can only contain letters and numbers</div>
            <div class="error" v-if="!$v.form.username.minLength">Username must have at least {{ $v.form.username.$params.minLength.min }}</div>
            <div class="error" v-if="!$v.form.username.maxLength">Username is too long. It can contains maximum {{ $v.form.username.$params.maxLength.max }} letters</div>
          </div>
        </div>
        <div class="form-group">
          <label for="emailAddress">Email address</label>
          <input type="email" class="form-control" id="emailAddress" v-model="form.emailAddress">
          <div class="field-error" v-if="$v.form.emailAddress.$dirty">
            <div class="error" v-if="!$v.form.emailAddress.required">Email address is required</div>
            <div class="error" v-if="!$v.form.emailAddress.email">This is not a valid email address</div>
            <div class="error" v-if="!$v.form.emailAddress.maxLength">Email address is too long. It can contains maxium {{$v.form.emailAddress.$params.maxLength.max}}</div>
          </div>
        </div>
        <div class="form-group">
          <label for="password">Password</label>
          <input type="password" class="form-control" id="password" v-model="form.password">
          <div class="field-error" v-if="$v.form.password.$dirty">
            <div class="error" v-if="!$v.form.password.minLength">Password must contain at leas {{$v.form.password.$params.minLength.min}}</div>
            <div class="error" v-if="!$v.form.password.maxLength">Password is too long. It can contains maximum {{$v.form.password.$params.maxLength.max}}</div>
          </div>
        </div>
        <button type="submit" class="btn btn-primary btn-block">
          Create Account
        </button>
      </form>
    </div>
  </div>
  <footer class="footer">
    <span class="copyright"> ... </span>
    <ul class="footer-links list-inline float-right">...</ul>
  </footer>
</div>
</template>

<script>
import registrationService from '../services/registration'
import { required, email, minLength, maxLength, alphaNum } from 'vuelidate/lib/validators'

export default {
  name: 'RegisterPage',
  data: function () {
    return {
      form: {
        username: '',
        emailAddress: '',
        password: ''
      },
      errorMessage: ''
    }
  },
  validations: {
    form: {
      username: {
        required,
        minLength: minLength(2),
        maxLength: maxLength(50),
        alphaNum
      },
      emailAddress: {
        required,
        email,
        maxLength: maxLength(100)
      },
      password: {
        required,
        minLength: minLength(6),
        maxLength: maxLength(40)
      }
    }
  },
  methods: {
    submitForm () {
      this.$v.$touch()
      if (this.$v.$invalid) {
        return
      }
      registrationService.register(this.form).then(() => {
        this.$router.push({ name: 'LoginPage' })
      }).catch((error) => {
        this.errorMessage = 'Failed to register user. Reason: ' + error.message
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  .container{max-width: 900px;}
  .register-form{margin-top: 50px; max-width: 325px;}
  .logo-wrapper{margin-bottom: 35px}
  .footer{width: 100%; line-height: 40px; margin-top: 50px;}

</style>
