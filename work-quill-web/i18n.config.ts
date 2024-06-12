export default defineI18nConfig(() => {
    const i18nCookie = useCookie("i18n_redirected");
    const locale = i18nCookie.value ? i18nCookie.value : "en";

    return {
        locale: locale,
        messages: {
            zh: {
                notify: {
                    welcome: "{name}，欢迎回来！",
                    no_token: "Token不存在, 请登录!",
                },
                login: {
                    title: "登录",
                    subtitle: "欢迎登录 Work Quill 系统",
                },
                logout: "退出登录",
                logout_already: "成功退出登录",
                language: "语言",
                nav: {
                    home: "主页",
                    accounts: "账户",
                    departments: "部门",
                    members: "成员",
                    salary: "薪资",
                },
                modifiable_table: {
                    actions: "编辑",
                    new_item: "新增",
                    no_data_available: "无可用数据",
                    btn: {
                        refresh: "刷新",
                    },
                },
                account: {
                    manage_account: "账号管理",
                },
                department: {
                    manage_department: "部门管理",
                },
                member: {
                    manage_member: "成员管理",
                },
                salary: {
                    manage_salary: "薪资管理",
                },
            },
            en: {
                notify: {
                    welcome: "Welcome back, {name}!",
                    no_token: "Token not exists, please login!",
                },
                login: {
                    title: "Login",
                    subtitle: "Welcome to log in Work Quill",
                },
                logout: "Logout",
                logout_already: "Successfully log out",
                language: "Language",
                nav: {
                    home: "Home",
                    accounts: "Accounts",
                    departments: "Departments",
                    members: "Members",
                    salary: "Salary",
                },
                modifiable_table: {
                    actions: "Actions",
                    new_item: "New Item",
                    no_data_available: "No data available",
                    btn: {
                        refresh: "Refresh",
                    },
                },
                account: {
                    manage_account: "Manage Account",
                },
                department: {
                    manage_department: "Manage Department",
                },
                member: {
                    manage_member: "Manage Member",
                },
                salary: {
                    manage_salary: "Manage Salary",
                },
            },
        },
    };
});
