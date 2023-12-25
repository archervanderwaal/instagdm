import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

const commonRoutes = [
  {
    path: "/login",
    name: "login",
    meta: { title: "登录" },
    component: () => import("../components/Login.vue"),
  },
  {
    path: "/404",
    name: "404",
    meta: { title: "404" },
    component: () => import("../components/404.vue"),
  },
  { path: "/", redirect: "/home" },
];

// 本地所有的页面 需要配合后台返回的数据生成页面
export const asyncRoutes = {
  home: {
    path: "home",
    name: "home",
    meta: { title: "主页" },
    component: () => import("../views/Home.vue"),
  },
  sendMessage: {
    path: "sendMessage",
    name: "sendMessage",
    meta: { title: "Ins群发消息" },
    component: () => import("../views/SendMessage.vue"),
  },
  threadsCreate: {
    path: "threadsCreate",
    name: "threadsCreate",
    meta: { title: "Ins创建群组" },
    component: () => import("../views/ThreadsCreate.vue"),
  },
  listThreads: {
    path: "listThreads",
    name: "listThreads",
    meta: { title: "群组列表" },
    component: () => import("../views/Threads.vue"),
  },
};

const createRouter = () =>
  new Router({
    routes: commonRoutes,
  });

const router = createRouter();

export function resetRouter() {
  const newRouter = createRouter();
  router.matcher = newRouter.matcher;
}

export default router;
