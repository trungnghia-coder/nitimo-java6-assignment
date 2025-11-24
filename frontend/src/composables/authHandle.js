import { ref } from 'vue'
import { useRouter } from 'vue-router'
import api from '../utils/api'

// Check auth status from backend
export async function isUserLoggedIn() {
    try {
        const res = await api.get('/api/auth/check-auth-status')
        return res.data.isAuthenticated === true
    } catch (err) {
        console.error('Error checking auth status:', err)
        return false
    }
}

export function useAuthHandle() {
    const router = useRouter()
    const username = ref('')
    const password = ref('')
    const rememberMe = ref(false)
    const errorMessage = ref('')

    const oldPassword = ref('')
    const newPassword = ref('')
    const confirmPassword = ref('')


    const login = async () => {
        try {
            const res = await api.post('/api/auth/login', {
                username: username.value,
                password: password.value,
                rememberMe: rememberMe.value
            })
            if (res.data.success) {
                alert('Login successful!')
                router.push('/')
            } else {
                errorMessage.value = res.data.message
            }
        } catch (err) {
            errorMessage.value = 'Server error, please try again later'
            console.error(err)
        }
    }

    const signup = async (fullName, email, password, confirmPassword) => {
        try {
            if (password !== confirmPassword) {
                errorMessage.value = 'Passwords do not match'
                return
            }

            const res = await api.post('/api/auth/logup', {
                fullName,
                email,
                password,
                confirmPassword
            })
            if (res.data.success) {
                alert('Account created successfully!')
                return true
            } else {
                errorMessage.value = res.data.message
                return false
            }
        } catch (err) {
            errorMessage.value = 'Server error, please try again later'
            console.error(err)
            return false
        }
    }

    const passwordChange = async (oldPass, newPass, confirmPass) => {
        try {
            if (newPass !== confirmPass) {
                errorMessage.value = 'Passwords do not match'
                return false
            }

            const res = await api.post('/api/auth/change-password', {
                oldPassword: oldPass,
                newPassword: newPass,
                confirmPassword: confirmPass
            })
            if (res.data.success) {
                alert('Password changed successfully!')
                return true
            } else {
                errorMessage.value = res.data.message
                return false
            }
        } catch (err) {
            errorMessage.value = 'Server error, please try again later'
            console.error(err)
            return false
        }
    }

    const resetPassword = async (email) => {
        try {
            const res = await api.post('/api/forgot-password', {
                email
            })
            if (res.data.success) {
                alert('Reset link sent to your email!')
                return true
            } else {
                errorMessage.value = res.data.message
                return false
            }
        } catch (err) {
            errorMessage.value = 'Server error, please try again later'
            console.error(err)
            return false
        }
    }

    const logout = async () => {
        try {
            await api.post('/api/auth/logout')
            alert('Logged out successfully!')
            router.push('/auth')
        } catch (err) {
            console.error('Logout error:', err)
            router.push('/')
        }
    }

    return {
        username,
        password,
        rememberMe,
        errorMessage,
        oldPassword,
        newPassword,
        confirmPassword,
        login,
        signup,
        resetPassword,
        passwordChange,
        logout
    }
}