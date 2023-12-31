import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    isShowLoading: false, // 全局 loading
    // 左侧菜单栏数据
    menuItems: [
      {
        name: "home", // 要跳转的路由名称 不是路径
        size: 18, // icon大小
        type: "md-home", // icon类型
        text: "主页", // 文本内容
      },
      {
        name: "sendMessage",
        size: 18,
        type: "ios-paper-plane",
        text: "Ins群发消息",
      },
      {
        name: "threadsCreate",
        size: 18,
        type: "ios-create",
        text: "Ins创建群组",
      },
      {
        name: "listThreads",
        size: 18,
        type: "ios-arrow-forward",
        text: "Ins群组列表",
      },
      {
        name: "collectFollowers",
        size: 18,
        type: "ios-build-outline",
        text: "Ins粉丝采集",
      },
      {
        name: "listFollowers",
        size: 18,
        type: "ios-book-outline",
        text: "Ins粉丝列表",
      },
    ],
  },
  mutations: {
    setMenus(state, items) {
      state.menuItems = [...items];
    },
    setLoading(state, isShowLoading) {
      state.isShowLoading = isShowLoading;
    },
  },
});

export default store;
