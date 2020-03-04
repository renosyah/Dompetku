# Keuanganku

adalah aplikasi pencatatan pemasukkan dan pengeluaran berbasis android mobile
menggunakan Android MVP Architecture dan juga ROOM untuk penyimpanan lokal


## MVP Architecture

Model-View-Presenter atau yang biasa disingkat menjadi MVP adalah sebuah konsep arsitektur pengembangan aplikasi yang memisahkan antara tampilan aplikasi dengan proses bisnis yang bekerja pada aplikasi. Arsitektur ini akan membuat pengembangan aplikasi kita menjadi lebih terstuktur, mudah di-test dan juga mudah di-maintain.

Berikut penjelasan masing-masing layer pada MVP.
- View, merupakan layer untuk menampilkan data dan interaksi ke user. View biasanya berupa Activity, Fragment atau Dialog di Android. View ini juga yang langsung berkomunikasi dengan user.
- Model, merupakan layer yang menunjuk kepada objek dan data yang ada pada aplikasi.
- Presenter, merupakan layer yang menghubungkan komunikasi antara Model dan View. Setiap interaksi yang dilakukan oleh user akan memanggil Presenter untuk memrosesnya dan mengakses Model lalu mengembalikan responnya kembali kepada View.


sumber : [medium MVP by Eminarti Sianturi
](https://medium.com/easyread/android-mvp-series-membangun-aplikasi-android-dengan-arsitektur-mvp-fbf1f77ecaec)

sumber : [github.com/MindorksOpenSource/android-mvp-architecture](https://github.com/MindorksOpenSource/android-mvp-architecture)




## Android ROOM

Room adalah perpustakaan kegigihan baru Google yang dirancang untuk
membuatnya lebih mudah untuk membangun aplikasi offline. 
Ia mencoba untuk mengekspos API yang dapat memanfaatkan 
kekuatan penuh SQL sambil tetap menyediakan lapisan abstraksi 
untuk mengelola data sebagai objek Java. Ini juga berfungsi dengan baik 
dengan perpustakaan Komponen Arsitektur Google untuk membangun aplikasi 
produksi berkualitas tinggi yang kuat dan juga dapat digunakan bersama 
dengan Paging Library untuk menangani kumpulan data besar.


untuk project ini terdapat tabel untuk menyimpan data transaksi yakni tabel transaksi

* Tabel Database Transaksi

![GitHub Logo](/design/releation.png) 



sumber : [github.com/codepath/android_guides/wiki/Room-Guide](https://github.com/codepath/android_guides/wiki/Room-Guide)




# Overview



## Splash Activity

adalah activity yang akan ditampilkan saat aplikasi di buka,
activity ini juga akan menjalankan service yang nantinya akan
melakukan monitor untuk transaksi yang expired

![GitHub Logo](/design/app_1_small.png) 




## Main Menu Activity

adalah activity yang akan menampilkan total saldo dan menu pengeluaran serta
satu menu laporan, dimana pada saat saldo di klik oleh user, maka akan masuk
ke form input pemasukkan, terdapat 3 menu pengeluaran dan satu menu laporan
menu-menu tersebut menggunakan recycleview dengan 4 item, jika salah satu item di klik
dan di validasi id menunya, maka akan menjalankan aktivity yg dituju sesuai dengan
id menu, contoh saat user menklik menu pengeluaran, maka user akan diarakan ke form
input pengeluaran

![GitHub Logo](/design/app_2_small.png) 




## Expense Activity

ini adalah activity yang digunakan untuk menginputkan transaksi pengeluaran
pada saat user menginputkan data-data, maka data-data tersebut akan di insert
ke tabel yang berada di database lokal yg menggunakan android ROOM

![GitHub Logo](/design/app_3_small.png) 




## Income Activity

ini adalah activity yang digunakan untuk menginputkan transaksi pemasukan
pada saat user menginputkan data-data, maka data-data tersebut akan di insert
ke tabel yang berada di database lokal yg menggunakan android ROOM,
berbeda dengan pengeluaran, activity ini juga akan menampilkan data-data pemasukkan
yg berbentuk list laporan, yg berada tepat dibawah form

![GitHub Logo](/design/app_4_small.png) 




## Report Menu Activity

ini adalah aktivity yang menampilkan 4 menu laporan yang ditampilkan menggunakan
recycleview, sama seperti pada menu utama, pada saat salah satu item diclik
maka akan divalidasi,lalu user akan diarahkan ke activity report diagram
yg akan menampilkan konten laporan sesuai dengan menu yg di klik oleh user

![GitHub Logo](/design/app_5_small.png) 




## Report Diagram Activity (List Report)

adalah salah satu konten dalam activity report diagram yg hanya akan ditampilkan ke user
apabila user memilih menu list report, report ini akan menujukan data laporan pemasukan dan
pengeluaran dalam bentuk tabel yg disusun dan diurutkan berdasarkan tanggal

![GitHub Logo](/design/app_6_small.png) 



## Report Diagram Activity (Line Report)

adalah salah satu konten dalam activity report diagram yg hanya akan ditampilkan ke user
apabila user memilih menu Line Report, report ini akan menujukan data laporan pemasukan dan
pengeluaran dalam bentuk line diagram yg disusun dan diurutkan berdasarkan tanggal

![GitHub Logo](/design/app_7_small.png) 




## Report Diagram Activity (Pie Chart Report)

adalah salah satu konten dalam activity report diagram yg hanya akan ditampilkan ke user
apabila user memilih menu Pie Chart Report, report ini akan menujukan total saldo pemasukan dan
pengeluaran dalam bentuk pie diagram untuk menujukan presentase besarnya pemasukan dan pengeluaran


![GitHub Logo](/design/app_8_small.png) 




## Report Diagram Activity (Waterfall Chart Report)

adalah salah satu konten dalam activity report diagram yg hanya akan ditampilkan ke user
apabila user memilih menu Waterfall Chart Report, report ini akan menujukan data naik turunnya saldo
dalam bentuk Waterfall diagram



![GitHub Logo](/design/app_9_small.png) 




## Setting Activity

adalah activity yang akan menentukan kapan user mau menerima notifikasi
pada saat ada transaksi yg expired terdeteksi


![GitHub Logo](/design/app_10_small.png) 




## Notification

adalah tampilan notifikasi bar untuk transaksi expired, saldo yang minus
dan selamat datang, untuk notif selamat datang untuk saat ini belum di implementasi

![GitHub Logo](/design/app_11_small.png) 



## Dialog Delete Transaction

adalah tampilan alert dialog yg akan menanyakan kepada user
apakah ia yakin ingin menghapus data transaksi yg dipilih

![GitHub Logo](/design/app_12_small.png) 