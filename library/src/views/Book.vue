<template>
    <div class="user-header">
        <el-button type="primary" @click="handleAdd">Add</el-button>
        <el-form :inline="true" :model="formInline">
            <el-form-item label="Please Enter">
                <el-input 
                    placeholder="Book Isbn" 
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
                :prop="item.prop"
                :label="item.label"
                :width="item.width"
                :fixed="item.fixed"
            >
                <template v-if="item.slot" #default="{ row }">
                    <span v-if="item.prop === 'available'">
                        {{ row.available ? 'Yes' : 'No' }}
                    </span>
                </template>
            </el-table-column>
            <el-table-column fixed="right" label="Operations" min-width="120">
                <template #="scope">
                    <el-button link type="primary" size="small" @click="handleEdit(scope.row)">Edit</el-button>
                    <el-button link type="danger" size="small" @click="handleDelete(scope.row)">Delete</el-button>
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
    :title="action === 'add' ? 'Add New Book' : 'Edit Book'"
    width="35%"
    >
        <el-form :model="formBook" label-width="110px">
            <el-row :gutter="20">
                <el-col :span="12">
                    <el-form-item label="Isbn">
                        <el-input v-model="formBook.isbn" />
                    </el-form-item>
                    <el-form-item label="Title">
                        <el-input v-model="formBook.title" />
                    </el-form-item>
                    <el-form-item label="Authors">
                        <el-input v-model="formBook.authors" />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="Publication Year">
                        <el-input type="number" v-model.number="formBook.publicationYear" />
                    </el-form-item>
                    <el-form-item label="Genre">
                        <el-input v-model="formBook.genre" />
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
const getAllBooks = async () => {
    const response = await proxy.$api.getAllBooks()
    tableData.value = response.data.data
}

const tableData = ref([])

const tableLabel = reactive([
    {
        prop:'isbn',
        label:'Isbn',
        width: 150
    },
    {
        prop:'title',
        label:'Title',
        width: 250
    },
    {
        prop:'authors',
        label:'Authors',
        width: 170
    },
    {
        prop:'publicationYear',
        label:'Publication Year',
        width:160
    },
    {
        prop:'genre',
        label:'Genre',
        width:150
    },
    {
        prop:'available',
        label:'Available',
        width:150,
        slot: true
    }
]) 

const dialogVisible = ref(false)

const action = ref('add')

const formBook = ref({
  isbn: '',
  title: '',
  authors: [],
  publicationYear: null,
  genre: ''
})

const bookId = ref()

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
  const response = await proxy.$api.getAllBooks()
  tableData.value = response.data.data
}

const handleSearch = async () => {
  const keyword = formInline.keyword.trim()
  currentPage.value = 1

  if (keyword) {
    const response = await proxy.$api.getBookByIsbn(keyword);
    tableData.value = response.data.data
  } else {
    const response = await proxy.$api.getAllBooks();
    tableData.value = response.data.data
  }
}

const resetForm = () => {
  formBook.value = {
    isbn: '',
    title: '',
    authors: [],
    publicationYear: null,
    genre: ''
  }
}

const handleAdd = ()=>{
    resetForm()
    dialogVisible.value = true
    action.value = 'add'
}

const handleEdit = (row) => {
  action.value = 'edit'
  bookId.value = row.id
  formBook.value = {
    isbn: row.isbn,
    title: row.title,
    authors: row.authors,
    publicationYear: row.publicationYear,
    genre: row.genre
  }
  dialogVisible.value = true
}

const onSubmit = async () => {
  if (action.value === 'edit') {
    await proxy.$api.updateBook(formBook.value, bookId.value)
  } else {
    await proxy.$api.addBook(formBook.value)
  }
  getAllBooks()
  dialogVisible.value = false
}


const handleDelete = async (row) => {
  tableData.value = tableData.value.filter(book => book.id !== row.id)
  await proxy.$api.deleteBook(row.id)
}

onMounted(()=>{
    getAllBooks()
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