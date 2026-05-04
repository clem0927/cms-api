<script setup>
import { ref } from 'vue'

const username = ref('')
const password = ref('')

const signup = async () => {
  try {
    const res = await fetch('http://localhost:8080/auth/signup', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        username: username.value,
        password: password.value
      })
    })

    if (res.ok) {
      alert('회원가입 성공')
      window.location.href = '/login'
    } else {
      alert('회원가입 실패')
    }
  } catch (e) {
    alert('에러 발생')
  }
}
</script>

<template>
  <div>
    <h2>회원가입</h2>

    <input v-model="username" placeholder="아이디" />
    <br />
    <input v-model="password" type="password" placeholder="비밀번호" />
    <br />

    <button @click="signup">회원가입</button>

    <p>
      이미 계정이 있나요?
      <a href="/login">로그인</a>
    </p>
  </div>
</template>