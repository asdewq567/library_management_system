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
                v-for="col in tableLabel"
                :key="col.prop"
                :prop="col.prop"
                :label="col.label"
                :width="col.width"
            >
                <template v-if="col.slot" #default="{ row }">
                    <span v-if="col.prop === 'bookId'">{{ getBookIsbn(row.bookId) }}</span>
                    <span v-else-if="col.prop === 'memberId'">{{ getMemberName(row.memberId) }}</span>
                    <span v-else-if="col.prop === 'fineAmount'">{{ row.fine ? row.fine.amount : '' }}</span>
                    <span v-else-if="col.prop === 'finePaid'">{{ row.fine ? (row.fine.paid ? 'Yes' : 'No') : '' }}</span>
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
                    <el-button link type="warning" size="small" @click="handleReturn(row)">
                        Return
                    </el-button>
                    <el-button link type="warning" size="small" @click="handlePay(row)">
                        Pay
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
    :title="action === 'add' ? 'Add New Loan' : 'Edit Loan'"
    width="55%"
    >
        <el-form :model="formLoan" label-width="110px">
            <el-row :gutter="20">
                <el-col :span="12">
                    <el-form>
                        <el-form-item label="Book ISBN">
                            <el-input
                                v-model="bookSearchInput"
                                placeholder="Enter book ISBN"
                                clearable
                                style="width: 250px;"
                            />
                        </el-form-item>

                        <el-form-item label="Member Name">
                            <el-input
                                v-model="memberSearchInput"
                                placeholder="Enter member name"
                                clearable
                                style="width: 250px; margin-left: 10px;"
                            />
                        </el-form-item>
                    </el-form>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="Checkout Date">
                        <el-date-picker
                            v-model="formLoan.checkoutDate"
                            type="date"
                            placeholder="Pick a date"
                            format="YYYY-MM-DD"
                            value-format="YYYY-MM-DD"
                            clearable
                        />
                    </el-form-item>
                    <el-form-item label="Due Date">
                        <el-date-picker
                            v-model="formLoan.dueDate"
                            type="date"
                            placeholder="Pick a date"
                            format="YYYY-MM-DD"
                            value-format="YYYY-MM-DD"
                            clearable
                        />
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

const getAllLoans = async () => {
    const response = await proxy.$api.getAllLoans()
    tableData.value = response.data.data
}

const tableData = ref([])

const tableLabel = reactive([
  { prop: 'bookId', label: 'Book ISBN', width: 160, slot: true },
  { prop: 'memberId', label: 'Member Name', width: 210, slot: true },
  { prop: 'checkoutDate', label: 'Checkout Date', width: 120 },
  { prop: 'dueDate', label: 'Due Date', width: 120 },
  { prop: 'returnDate', label: 'Return Date', width: 120 },
  { prop: 'fine.amount', label: 'Amount', width: 120, slot: true },
  { prop: 'fine.paid', label: 'Paid', width: 150, slot: true }
])


const dialogVisible = ref(false)

const action = ref('add')

const formLoan = ref({
  bookId: null,
  memberId: null,
  checkoutDate: null,
  dueDate: null
})

const bookSearchInput = ref('')
const memberSearchInput = ref('')

const loanId = ref()

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

const handleSearch = async () => {
  const keyword = formInline.keyword.trim()
  currentPage.value = 1

  if (!keyword) {
    await getAllLoans()
    return
  }

  const matchedBook = allBooks.value.find(book => book.isbn === keyword)
  const allLoansResponse = await proxy.$api.getAllLoans()
  const allLoans = allLoansResponse.data.data
  tableData.value = allLoans.filter(loan => loan.bookId === matchedBook.id)
}

const handleClear = async () => {
  currentPage.value = 1
  const response = await proxy.$api.getAllLoans()
  tableData.value = response.data.data
}

const handleReturn = async (row) => {
    const returnDate = new Date().toISOString().split('T')[0]
    const dto = {returnDate: returnDate}
    await proxy.$api.updateReturnDate(row.id, dto)
    await getAllLoans()
}

const handlePay = async (row) => {
    await proxy.$api.payFine(row.fine.id)
    await getAllLoans()
}

const resetForm = () => {
  formLoan.value = {
    bookId: null,
    memberId: null,
    checkoutDate: null,
    dueDate: null
  }
}

const handleAdd = ()=>{
    resetForm()
    dialogVisible.value = true
    action.value = 'add'
}

const handleEdit = (row) => {
  action.value = 'edit'
  loanId.value = row.id
  const book = allBooks.value.find(b => b.id === row.bookId)
  const member = allMembers.value.find(m => m.id === row.memberId)

  bookSearchInput.value = book?.isbn || ''
  memberSearchInput.value = member?.name || ''

  formLoan.value = {
    bookId: row.bookId,
    memberId: row.memberId,
    checkoutDate: row.checkoutDate,
    dueDate: row.dueDate
  }

  dialogVisible.value = true
}


const onSubmit = async () => {
  const selectedBook = allBooks.value.find(book => book.isbn === bookSearchInput.value.trim())
  const selectedMember = allMembers.value.find(member => member.name === memberSearchInput.value.trim())

  if (!selectedBook || !selectedMember) {
    proxy.$message.error('Book ISBN or Member Name not found')
    return
  }
  
  formLoan.value.bookId = selectedBook.id
  formLoan.value.memberId = selectedMember.id

  if (action.value === 'edit') {
    await proxy.$api.updateLoan(formLoan.value, loanId.value)
  } else {
    await proxy.$api.addLoan(formLoan.value)
  }
  getAllLoans()
  dialogVisible.value = false
}

const handleDelete = async (row) => {
  tableData.value = tableData.value.filter(loan => loan.id !== row.id)
  await proxy.$api.deleteLoan(row.id)
}

const allBooks = ref([])
const allMembers = ref([])

const fetchReferences = async () => {
  const booksResponse = await proxy.$api.getAllBooks()
  const membersResponse = await proxy.$api.getAllMembers()

  allBooks.value = booksResponse.data.data
  allMembers.value = membersResponse.data.data
}

const getBookIsbn = (bookId) => {
  return allBooks.value.find(book => book.id === bookId)?.isbn || ''
}

const getMemberName = (memberId) => {
  return allMembers.value.find(member => member.id === memberId)?.name || ''
}
onMounted(async () => {
  await fetchReferences()
  await getAllLoans()
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