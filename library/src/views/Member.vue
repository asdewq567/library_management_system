<template>
    <div class="user-header">
        <el-button type="primary" @click="handleAdd">Add</el-button>
        <el-form :inline="true" :model="formInline">
            <el-form-item label="Please Enter">
                <el-input 
                    placeholder="Username" 
                    v-model="formInline.keyword"
                    clearable
                    @clear="handleClear"
                />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="handleSearch">Search</el-button>
            </el-form-item>
        </el-form>
    </div>
    <div class="table">
        <el-table :data="pagedData" style="width: 100%">
            <el-table-column
                v-for="item in tableLabel"
                :key="item.prop"
                :label="item.label"
                :width="item.width"
                :prop="!item.slot ? item.prop : undefined"
            >
                <template v-if="item.slot" #default="{ row }">
                    <span v-if="item.prop === 'address'">
                        {{ row.address.houseNumber }}, {{ row.address.streetName }}, {{ row.address.zipCode }}
                    </span>
                    <span v-else-if="item.prop === 'active'">
                        {{ row.active ? 'Yes' : 'No' }}
                    </span>
                </template>
            </el-table-column>
            <el-table-column fixed="right" label="Operations" min-width="120">
                <template #default="{ row }">
                    <el-button link type="primary" size="small" @click="handleDelete(row)">
                        Delete
                    </el-button>
                    <el-button link type="primary" size="small" @click="handleEdit(row)">
                        Edit
                    </el-button>
                    <el-button link type="warning" size="small" @click="toggleActive(row)">
                        {{ row.active ? 'Deactivate' : 'Activate' }}
                    </el-button>
                </template>
            </el-table-column>
        </el-table> 
        <el-pagination 
            class="pager"
            background 
            layout="prev, pager, next" 
            size="small"
            :page-size="pageSize"
            :total="tableData.length" 
            @current-change="handleChange"
        />  
    </div>

    <el-dialog
    v-model="dialogVisible"
    :title="action === 'add' ? 'Add New Member' : 'Edit Member'"
    width="35%"
    >
        <el-form :model="formBook" label-width="110px">
            <el-row :gutter="20">
                <el-col :span="12">
                    <el-form-item label="Username">
                        <el-input v-model="formMember.name" />
                    </el-form-item>
                    <el-form-item label="Email">
                        <el-input v-model="formMember.email" />
                    </el-form-item>
                    <el-form-item label="Phone">
                        <el-input v-model="formMember.phone" />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="Street Name">
                        <el-input v-model="formMember.address.streetName" />
                    </el-form-item>
                    <el-form-item label="House Number">
                        <el-input v-model="formMember.address.houseNumber" />
                    </el-form-item>
                    <el-form-item label="Zipcode">
                        <el-input v-model="formMember.address.zipCode" />
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row style="justify-content: flex-end;">
                <el-form-item>
                    <el-button @click="dialogVisible = false">Cancel</el-button>
                    <el-button type="primary" @click="onSubmit">Save</el-button>
                </el-form-item>
            </el-row>
        </el-form>
    </el-dialog>
</template>

<script setup>
import {ref, getCurrentInstance, onMounted, reactive, computed} from 'vue'
const {proxy} = getCurrentInstance()
const getAllMembers = async () => {
    const response = await proxy.$api.getAllMembers()
    tableData.value = response.data.data
}

const tableData = ref([])

const tableLabel = reactive([
    {
        prop:'name',
        label:'Name',
        width: 210
    },
    {
        prop:'email',
        label:'Email',
        width: 210
    },
    {
        prop:'phone',
        label:'Phone',
        width: 170
    },
    {
        prop:'address',
        label:'Address',
        width:270,
        slot: true
    },
    {
        prop:'active',
        label:'Active',
        width:100,
        slot: true
    }
]) 

const dialogVisible = ref(false)

const action = ref('add')

const formMember = ref({
  name: '',
  email: '',
  phone: '',
  address: {
    streetName: '',
    houseNumber: '',
    zipCode: ''
  }
})

const memberId = ref()

const formInline = reactive({
    keyword:''
})

const currentPage = ref(1)

const pageSize = 10

const pagedData = computed(() => {
  const start = (currentPage.value - 1) * pageSize
  return tableData.value.slice(start, start + pageSize)
})

const handleChange = (page) => {
  currentPage.value = page
}

const handleClear = async () => {
  currentPage.value = 1
  const response = await proxy.$api.getAllMembers()
  tableData.value = response.data.data
}

const handleSearch = async () => {
  const keyword = formInline.keyword.trim();
  currentPage.value = 1

  if (keyword) {
    const response = await proxy.$api.getMemberByName(keyword);
    tableData.value = response.data.data;
  } else {
    const response = await proxy.$api.getAllMembers();
    tableData.value = response.data.data;
  }
}

const toggleActive = async (row) => {
  await proxy.$api.updateMemberStatus(row.id);
  getAllMembers();
}

const resetForm = () => {
  formMember.value = {
    name: '',
    email: '',
    phone: '',
    address: {
        streetName: '',
        houseNumber: '',
        zipCode: ''
    }
  }
}

const handleAdd = ()=>{
    resetForm()
    dialogVisible.value = true
    action.value = 'add'
}

const handleEdit = (row) => {
  action.value = 'edit'
  memberId.value = row.id
  formMember.value = {
    name: row.name,
    email: row.email,
    phone: row.phone,
    address: row.address
  }
  dialogVisible.value = true
}

const onSubmit = async () => {
  if (action.value === 'edit') {
    await proxy.$api.updateMember(formMember.value, memberId.value)
  } else {
    await proxy.$api.addMember(formMember.value)
  }
  getAllMembers()
  dialogVisible.value = false
}

const handleDelete = async (row) => {
  tableData.value = tableData.value.filter(member => member.id !== row.id)
  await proxy.$api.deleteMember(row.id)
}

onMounted(()=>{
    getAllMembers()
})
</script>

<style scoped>
.user-header{
    display:flex;
    justify-content:space-between;
}

.table{
    position:relative;
    height:520px;
    .pager{
        position:absolute;
        right:10px;
        bottom:30px;
    }
    .el-table{
        width:100%;
        height:500px;
    }
}

.select-clearn{
    display:flex;
}
</style>