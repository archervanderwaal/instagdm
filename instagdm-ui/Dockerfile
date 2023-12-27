# 基础镜像
FROM node:16.0-alpine as build-stage

# 设置工作目录
WORKDIR /app

# 安装 cnpm
RUN npm install -g cnpm --registry=https://registry.npm.taobao.org

# 复制 package.json 和 package-lock.json（如果可用）
COPY package*.json ./

# 使用 cnpm 安装项目依赖
RUN cnpm install

# 复制项目文件
COPY . .

# 构建应用
RUN npm run build

# 生产阶段
FROM nginx:stable-alpine as production-stage

# 从构建阶段复制构建出的文件
COPY --from=build-stage /app/dist /usr/share/nginx/html

# 暴露 80 端口
EXPOSE 80

# 启动 nginx
CMD ["nginx", "-g", "daemon off;"]