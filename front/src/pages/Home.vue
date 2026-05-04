<script setup>
import { ref, onMounted } from 'vue'
import api from '../api/auth'
const user = ref(null)

onMounted(async () => {
  try {
    const res = await api.get('/auth/me')
    user.value = res.data
  } catch (e) {
    user.value = null
  }
})
</script>

<template>
  <div>
    <h1>홈</h1>

    <!-- 로그인 안됨 -->
    <div v-if="!user">
      <p>로그인이 필요합니다</p>
      <router-link to="/login">로그인</router-link>
    </div>

    <!-- 로그인 됨 -->
    <div v-else>
      <p>{{ user.username }}님 환영합니다</p>

      <!-- 관리자만 -->
      <button v-if="user.roles.includes('ROLE_ADMIN')">
        관리자 기능
      </button>

      <!-- 일반 사용자 -->
      <button v-if="user.roles.includes('ROLE_USER')">
        일반 기능
      </button>
    </div>
  </div>
</template>