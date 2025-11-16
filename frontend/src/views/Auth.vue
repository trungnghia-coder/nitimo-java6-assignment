<template>
  <div class="login-container">
    <!-- Left Side - Image -->
    <div class="login-image">
      <img src="https://theme.hstatic.net/200000810809/1001159064/14/about_slide_1_img.jpg?v=353" alt="Login" class="img-full"/>
    </div>

    <!-- Right Side - Form -->
    <div class="login-form-section">
      <div class="login-form-wrapper">
        <!-- Login Form -->
        <div v-if="currentForm === 'login'" class="form-slide-in">
          <div class="login-header">
            <h1 class="login-title">Welcome Back</h1>
            <p class="login-subtitle">Sign in to your account</p>
          </div>

          <form class="login-form" @submit.prevent="handleLogin">
            <!-- Email Input -->
            <div class="form-group">
              <label class="form-label">Email Address</label>
              <input 
                v-model="email" 
                type="email" 
                class="form-control" 
                placeholder="Enter your email"
                required
              />
            </div>

            <!-- Password Input -->
            <div class="form-group">
              <label class="form-label">Password</label>
              <input 
                v-model="password" 
                type="password" 
                class="form-control" 
                placeholder="Enter your password"
                required
              />
            </div>

            <!-- Remember Me & Forgot Password -->
            <div class="form-footer">
              <div class="form-check">
                <input 
                  v-model="rememberMe" 
                  type="checkbox" 
                  class="form-check-input" 
                  id="rememberMe"
                />
                <label class="form-check-label" for="rememberMe">
                  Remember me
                </label>
              </div>
              <a href="#" @click.prevent="currentForm = 'forgot'" class="forgot-password">Forgot Password?</a>
            </div>

            <!-- Login Button -->
            <button type="submit" class="btn btn-orange w-100 fw-bold py-2">
              Sign In
            </button>
          </form>

          <!-- Sign Up Link -->
          <div class="login-signup">
            <p>Don't have an account? <a href="#" @click.prevent="currentForm = 'signup'" class="signup-link">Sign Up</a></p>
          </div>

          <!-- Divider -->
          <div class="divider">
            <span>or</span>
          </div>

          <!-- Social Login -->
          <div class="social-login">
            <button type="button" class="btn btn-social google-btn">
              <i class="fab fa-google"></i> Google
            </button>
            <button type="button" class="btn btn-social facebook-btn">
              <i class="fab fa-facebook"></i> Facebook
            </button>
          </div>
        </div>

        <!-- Forgot Password Form -->
        <div v-if="currentForm === 'forgot'" class="form-slide-in">
          <div class="login-header">
            <h1 class="login-title">Reset Password</h1>
            <p class="login-subtitle">Enter your email to reset your password</p>
          </div>

          <form class="login-form" @submit.prevent="handleForgotPassword">
            <!-- Email Input -->
            <div class="form-group">
              <label class="form-label">Email Address</label>
              <input 
                v-model="resetEmail" 
                type="email" 
                class="form-control" 
                placeholder="Enter your email"
                required
              />
            </div>

            <!-- Reset Button -->
            <button type="submit" class="btn btn-orange w-100 fw-bold py-2">
              Send Reset Link
            </button>
          </form>

          <!-- Back to Login -->
          <div class="login-signup">
            <p><a href="#" @click.prevent="currentForm = 'login'" class="signup-link">← Back to Login</a></p>
          </div>
        </div>

        <!-- Sign Up Form -->
        <div v-if="currentForm === 'signup'" class="form-slide-in">
          <div class="login-header">
            <h1 class="login-title">Create Account</h1>
            <p class="login-subtitle">Sign up to get started</p>
          </div>

          <form class="login-form" @submit.prevent="handleSignUp">
            <!-- Name Input -->
            <div class="form-group">
              <label class="form-label">Full Name</label>
              <input 
                v-model="signupName" 
                type="text" 
                class="form-control" 
                placeholder="Enter your full name"
                required
              />
            </div>

            <!-- Email Input -->
            <div class="form-group">
              <label class="form-label">Email Address</label>
              <input 
                v-model="signupEmail" 
                type="email" 
                class="form-control" 
                placeholder="Enter your email"
                required
              />
            </div>

            <!-- Password Input -->
            <div class="form-group">
              <label class="form-label">Password</label>
              <input 
                v-model="signupPassword" 
                type="password" 
                class="form-control" 
                placeholder="Enter your password"
                required
              />
            </div>

            <!-- Confirm Password Input -->
            <div class="form-group">
              <label class="form-label">Confirm Password</label>
              <input 
                v-model="signupConfirmPassword" 
                type="password" 
                class="form-control" 
                placeholder="Confirm your password"
                required
              />
            </div>

            <!-- Sign Up Button -->
            <button type="submit" class="btn btn-orange w-100 fw-bold py-2">
              Create Account
            </button>
          </form>

          <!-- Back to Login -->
          <div class="login-signup">
            <p>Already have an account? <a href="#" @click.prevent="currentForm = 'login'" class="signup-link">Sign In</a></p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import '../assets/css/login.css'

const currentForm = ref('login')

// Login form
const email = ref('')
const password = ref('')
const rememberMe = ref(false)

// Forgot password form
const resetEmail = ref('')

// Sign up form
const signupName = ref('')
const signupEmail = ref('')
const signupPassword = ref('')
const signupConfirmPassword = ref('')

const handleLogin = async () => {
  if (!email.value || !password.value) {
    alert('Please fill in all fields')
    return
  }

  try {
    console.log('Login attempt:', {
      email: email.value,
      password: password.value,
      rememberMe: rememberMe.value
    })
    alert('Login successful!')
  } catch (error) {
    alert('Login failed: ' + error.message)
  }
}

const handleForgotPassword = async () => {
  if (!resetEmail.value) {
    alert('Please enter your email')
    return
  }

  try {
    console.log('Reset password for:', resetEmail.value)
    alert('Reset link sent to your email!')
    currentForm.value = 'login'
    resetEmail.value = ''
  } catch (error) {
    alert('Error: ' + error.message)
  }
}

const handleSignUp = async () => {
  if (!signupName.value || !signupEmail.value || !signupPassword.value || !signupConfirmPassword.value) {
    alert('Please fill in all fields')
    return
  }

  if (signupPassword.value !== signupConfirmPassword.value) {
    alert('Passwords do not match')
    return
  }

  try {
    console.log('Sign up:', {
      name: signupName.value,
      email: signupEmail.value,
      password: signupPassword.value
    })
    alert('Account created successfully!')
    currentForm.value = 'login'
    signupName.value = ''
    signupEmail.value = ''
    signupPassword.value = ''
    signupConfirmPassword.value = ''
  } catch (error) {
    alert('Sign up failed: ' + error.message)
  }
}
</script>
