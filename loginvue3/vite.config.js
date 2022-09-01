import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import { resolve } from 'path';

function pathResolve(dir) {
  return resolve(__dirname, ".", dir);
}
// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: [
        {
          find: /\/@\//,
          replacement: pathResolve('src') + '/'
        }
      ],
    extensions:['.mjs', '.js', '.ts', '.jsx', '.tsx', '.json', '.vue']
  },
  // css: {
  //   preprocessorOptions: {
  //     less: {
  //       javascriptEnabled: true,
  //     },
  //   },
  // },
  server:{
    cors: true,
    https: false,
    proxy: {
      '/api/auth': {
        target: 'http://127.0.0.1:9001/',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '')
      }
    }
  }
})
