import {createRouter, createWebHashHistory} from 'vue-router';

const routes = [
    {
        path:'/',
        name:'main',
        component: () => import("@/views/Main.vue"),
        redirect: "/book",
        children:[
            {
                path:'book',
                name:'book',
                component: () => import("@/views/Book.vue"),
            },
            {
                path:'member',
                name:'member',
                component: () => import("@/views/Member.vue"),
            },
            {
                path:'loan',
                name:'loan',
                component: () => import("@/views/Loan.vue"),
            },
        ]
    },
];

const router = createRouter({
    history: createWebHashHistory(),
    routes,
});

export default router;