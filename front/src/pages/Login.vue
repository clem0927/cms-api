<script setup>
import { ref } from 'vue'

const username = ref('')
const password = ref('')

const login = async () => {
  try {
    const res = await fetch('http://localhost:8080/auth/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        username: username.value,
        password: password.value
      })
    })

    const data = await res.json()

    // 🔥 JWT 저장
    localStorage.setItem('token', data.accessToken)

    alert('로그인 성공')

    // 👉 페이지 이동 (선택)
    window.location.href = '/'
  } catch (e) {
    alert('로그인 실패')
  }
}
</script>

<template>
  <div>
    <h2>로그인</h2>

    <input v-model="username" placeholder="아이디" />
    <br />
    <input v-model="password" type="password" placeholder="비밀번호" />
    <br />

    <button @click="login">로그인</button>

    <p>
      회원이 아니신가요?
      <a href="/signup">회원가입</a>
    </p>
  </div>
</template>