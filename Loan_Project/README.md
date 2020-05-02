create following table in database...
In hr/hr account

create table user_data(accountNum number primary key,password varchar2(20),name varchar2(30),gender varchar2(8),loanamount number,loanbal number,emi number,duration number);

create table user_transactions(transactionid number primary key,statement varchar2(200),accountnum number references user_data(accountnum));