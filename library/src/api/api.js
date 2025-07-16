import axios from 'axios'

const BASE_URL = 'http://localhost:8080'

export default{
    getBookByIsbn(bookIsbn){
        return axios.get(`${BASE_URL}/books/isbn/${bookIsbn}`)
    },
    getAllBooks(){
        return axios.get(`${BASE_URL}/books/batch`)
    },
    addBook(book){
        return axios.post(`${BASE_URL}/books`, book)
    },
    updateBook(book, bookId){
        return axios.put(`${BASE_URL}/books/update/${bookId}`, book)
    },
    deleteBook(bookId){
        return axios.delete(`${BASE_URL}/books/delete/${bookId}`)
    },
    getMemberByName(memberName){
        return axios.get(`${BASE_URL}/members/name/${memberName}`)
    },
    getAllMembers(){
        return axios.get(`${BASE_URL}/members/batch`)
    },
    addMember(member){
        return axios.post(`${BASE_URL}/members`, member)
    },
    updateMember(member, memberId){
        return axios.put(`${BASE_URL}/members/update/${memberId}`, member)
    },
    deleteMember(memberId){
        return axios.delete(`${BASE_URL}/members/delete/${memberId}`)
    },
    updateMemberStatus(memberId){
        return axios.patch(`${BASE_URL}/members/status/${memberId}`)
    },
    getAllLoans(){
        return axios.get(`${BASE_URL}/loans/batch`)
    },
    addLoan(loan){
        return axios.post(`${BASE_URL}/loans`, loan)
    },
    updateLoan(loan, loanId){
        return axios.put(`${BASE_URL}/loans/update/${loanId}`, loan)
    },
    deleteLoan(loanId){
        return axios.delete(`${BASE_URL}/loans/delete/${loanId}`)
    },
    updateReturnDate(loanId, dto){
        return axios.patch(`${BASE_URL}/loans/returnDate/${loanId}`, dto)
    },
    payFine(fineId){
        return axios.patch(`${BASE_URL}/fines/status/${fineId}`)
    },
}