# Service Package

adalah kumpulan program yg tidak memiliki interface atau tampilan, namun memiliki
peran dalam aplikasi seperti menangkap event dari sistem dan menggunakannya untuk
kebutuhan aplikasi, agar dapat melakukan hal tersebut, setiap class service memiliki
receiver, service yg tersedia yakni 

- service notifikasi

service ini bertujuan untuk mengirimkan user notifikasi saat
terdeteksi transaksi yg expire


# Receiver

adalah penerima broadcast yg akan menangkap setiap event yg diberikan oleh sistem
contoh event setiap menit berganti dan juga untuk mengaktifkan service yg di kill
oleh system dan user