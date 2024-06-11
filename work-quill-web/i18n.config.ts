export default defineI18nConfig(() => {
    return {
        locale: "en",
        messages: {
            zh: {
                welcome: "欢迎回来",
                logout: "退出登录",
                home: "主页",
                accounts: "账户",
                departments: "部门",
                members: "成员",
                salary: "薪资",
            },
            en: {
                welcome: "Welcome back",
                logout: "Logout",
                home: "Home",
                accounts: "Accounts",
                departments: "Departments",
                members: "Members",
                salary: "Salary",
            },
        },
    };
});
