# 基于指定版本的Node官方镜像
FROM node:16-alpine as build-stage

# 设置工作目录
WORKDIR /app

# 复制package.json和package-lock.json（如果存在）
COPY package*.json ./

# 安装项目依赖
RUN npm install

# 复制项目文件和文件夹到工作目录
COPY . .

# 构建应用
RUN npm run build

# 生产阶段使用nginx
FROM nginx:stable-alpine as production-stage

# 从构建阶段复制构建结果到nginx目录
COPY --from=build-stage /app/dist /usr/share/nginx/html

# 暴露80端口
EXPOSE 80

# 运行nginx
CMD ["nginx", "-g", "daemon off;"]
