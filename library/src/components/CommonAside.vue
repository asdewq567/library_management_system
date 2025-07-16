<template>
    <el-aside :width="width">
        <el-menu
         background-color="#545c64"
         text-color="#fff"
         :collapse="isCollapse"
         :collapse-transition="false"
         :default-active="activeMenu"
        >
        <h3 v-show="!isCollapse">Management System</h3>
        <h3 v-show="isCollapse">Menu</h3>
        <el-menu-item 
            v-for="item in menuList"
            :index="item.path"
            :key="item.path"
            @click="handleMenu(item)"
        >
          <component class="icons" :is="item.icon"></component>
          <span>{{ item.label }}</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
</template>

<script setup>
import {ref,computed} from 'vue'
import { useAllDataStore } from '@/stores'
import {useRouter, useRoute} from 'vue-router'
const router = useRouter()
const route = useRoute()
const store = useAllDataStore()
const menuList = ref([
            {
              path: '/book',
              name: 'book',
              label: 'Book',
              icon: 'house',
              url: 'Book'
            },
            {
              path: '/member',
              name: 'member',
              label: 'Member',
              icon: 'video-play',
              url: 'Member'
            },
            {
              path: '/loan',
              name: 'loan',
              label: 'Loan',
              icon: 'user',
              url: 'Loan'
            }
          ])
const isCollapse = computed(()=>store.state.isCollapse)
const width = computed(()=>store.state.isCollapse ? '64px' : '180px')
const handleMenu = (item) => {
    router.push(item.path)
    store.selectMenu(item)
}
const activeMenu = computed(()=>route.path)
</script>

<style scoped>

.icons{
    width:18px;
    height:18px;
    margin-right:5px;
}

.el-menu{
    border-right:none;
    h3{
        line-height:48px;
        color:#fff;
        text-align:center;
    }
}

.el-aside{
    height:100%;
    background-color:#545c64;
}
</style>