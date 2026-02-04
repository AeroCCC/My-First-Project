import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  define: {
    global: {}
  },
  plugins: [vue()],
  server: {
    port: 5175,
    proxy: {
      '/ws': {
        target: 'http://localhost:8080',
        ws:true,
        changeOrigin: true
      }
    }
  }
})
