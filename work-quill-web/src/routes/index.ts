interface NavRouteDataParameter {
    userTitle: string | null | undefined;
}

export function getNavRouteData(args: NavRouteDataParameter) {
    return [
        {
            props: {
                prependIcon: "mdi-account-circle",
            },
            title: args.userTitle,
            needsTranslate: false,
        },
        {
            props: {
                prependIcon: "mdi-home",
                link: true,
                to: "/",
            },
            title: "home",
            needsTranslate: true,
        },
        {
            props: {
                prependIcon: "mdi-account",
                link: true,
                to: "/account",
            },
            title: "accounts",
            needsTranslate: true,
        },
        {
            type: "divider",
        },
        {
            props: {
                prependIcon: "mdi-account-file",
                link: true,
                to: "/department",
            },
            title: "departments",
            needsTranslate: true,
        },
        {
            props: {
                prependIcon: "mdi-account-group",
                link: true,
                to: "/member",
            },
            title: "members",
            needsTranslate: true,
        },
        {
            props: {
                prependIcon: "mdi-cash",
                link: true,
                to: "/salary",
            },
            title: "salary",
            needsTranslate: true,
        },
    ];
}
