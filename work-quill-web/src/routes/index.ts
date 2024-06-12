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
            needsRole: [],
        },
        {
            props: {
                prependIcon: "mdi-home",
                link: true,
                to: "/",
            },
            title: "nav.home",
            needsTranslate: true,
            needsRole: [],
        },
        {
            props: {
                prependIcon: "mdi-account",
                link: true,
                to: "/account",
            },
            title: "nav.accounts",
            needsTranslate: true,
            needsRole: ["ROLE_ADMIN"],
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
            title: "nav.departments",
            needsTranslate: true,
            needsRole: ['ROLE_ADMIN'],
        },
        {
            props: {
                prependIcon: "mdi-account-group",
                link: true,
                to: "/member",
            },
            title: "nav.members",
            needsTranslate: true,
            needsRole: ['ROLE_ADMIN'],
        },
        {
            props: {
                prependIcon: "mdi-cash",
                link: true,
                to: "/salary",
            },
            title: "nav.salary",
            needsTranslate: true,
            needsRole: ['ROLE_ADMIN'],
        },
    ];
}
