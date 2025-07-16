<template>
    <div class="header">
        <div class="l-content">
            <el-button size="small" @click="handleCollapse">
                <component class="icons" is="menu"></component>
            </el-button>
            <el-breadcrumb separator="/" class="bread">
                <el-breadcrumb-item v-if="current" :to="current.path">{{ current.label }}</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="r-content">
            <el-dropdown>
                <span class="el-dropdown-link">
                    <img :src="getImageUrl('user')" class="user"/>  
                </span>
            </el-dropdown>
        </div>
    </div>
</template>

<script setup>
import {computed} from 'vue'
import { useAllDataStore } from '@/stores'
import {useRouter} from 'vue-router'
const getImageUrl = (user)=>{
    return new URL(`../assets/${user}.png`,import.meta.url).href
}

const store = useAllDataStore()
const handleCollapse = ()=>{
    store.state.isCollapse = !store.state.isCollapse
}
const router = useRouter()
const current = computed(()=>store.state.currentMenu)
</script>

<style scoped>
.header{
    display:flex;
    justify-content:space-between;
    align-items:center;
    width:100%;
    height:100%;
    background-color:#333;
}
.icons{
    width:20px;
    height:20px;
}

.l-content{
    display:flex;
    align-items:center;
    .el-button{
        margin-right:20px;
    }
}

.r-content{
    .user{
        width:40px;
        height:40px;
        border-radius:50%;
    }
}

:deep(.bread span){
    color:#fff !important;
    cursor:pointer !important;
}
</style>