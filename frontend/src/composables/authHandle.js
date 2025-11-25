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
    const successMessage = ref('')

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
                successMessage.value = 'Account created successfully!'
                setTimeout(() => { successMessage.value = '' }, 3000)
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
                successMessage.value = 'Password changed successfully!'
                setTimeout(() => { successMessage.value = '' }, 3000)
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
                successMessage.value = 'Reset link sent to your email!'
                setTimeout(() => { successMessage.value = '' }, 3000)
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
            successMessage.value = 'Logged out successfully!'
            setTimeout(() => { successMessage.value = '' }, 3000)
            router.push('/auth')
        } catch (err) {
            console.error('Logout error:', err)
            router.push('/')
        }
    }

    const profile = ref({
        fullName: '',
        email: '',
        phone: '',
        address: ''
    })

    async function fetchProfile() {
        try {
            const response = await fetch('/api/auth/get_my_profile', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include',
                cache: 'no-store'
            })

            const text = await response.text()

            if (!response.ok) {
                throw new Error(`Failed to fetch profile: ${response.status}`)
            }

            const data = JSON.parse(text)
            profile.value = {
                fullName: data.fullName || '',
                email: data.email || '',
                phone: data.phone || '',
                address: data.address || ''
            }
        } catch (err) {
            console.error('Error fetching profile:', err)
            throw err
        }
    }

    const informationUpdate = async (fullName, email, phone, address) => {
        try {
            // Kiểm tra dữ liệu trống
            if (!fullName || !email || !phone || !address) {
                errorMessage.value = 'Please fill in all fields'
                return false
            }

            // Validate email
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
            if (!emailRegex.test(email)) {
                errorMessage.value = 'Invalid email address'
                return false
            }

            // Validate phone (số điện thoại Việt Nam)
            const phoneRegex = /^(0\d{9,10})$/
            if (!phoneRegex.test(phone)) {
                errorMessage.value = 'Invalid phone number'
                return false
            }

            const res = await api.put('/api/auth/update-profile', {
                fullName,
                email,
                phone,
                address
            })

            if (res.data.success) {
                successMessage.value = 'Profile updated successfully!'
                setTimeout(() => { successMessage.value = '' }, 3000)
                errorMessage.value = ''
                return true
            } else {
                errorMessage.value = res.data.message || 'Update failed'
                return false
            }
        } catch (err) {
            errorMessage.value = err.response?.data?.message || 'Server error, please try again later'
            console.error('Update error:', err)
            return false
        }
    }

    return {
        username,
        password,
        rememberMe,
        errorMessage,
        successMessage,
        oldPassword,
        newPassword,
        confirmPassword,
        login,
        signup,
        resetPassword,
        passwordChange,
        logout,
        profile,
        fetchProfile,
        informationUpdate
    }
}