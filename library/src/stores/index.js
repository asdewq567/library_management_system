import { defineStore } from 'pinia';
import { ref } from 'vue';
function initState(){
    return{
        isCollapse: false,
        tags: [
            {
                path:'/book',
                name:'book',
                label:'Book'
            }
        ],
        currentMenu:null,
    };
}

export const useAllDataStore = defineStore('allData', () => {
    const state = ref(initState());

    function selectMenu(val){
        if(val.name == 'book'){
            state.value.currentMenu = null
        }else{
            state.value.currentMenu = val
            let index = state.value.tags.findIndex((item) => item.name === val.name)
            index === -1 ? state.value.tags.push(val) : ""
        }
    }

    function updateTags(tag){
        let index = state.value.tags.findIndex((item)=>item.name === tag.name)
        state.value.tags.splice(index, 1)
    }

    return {
        state,
        selectMenu,
        updateTags,
    };
})  