package com.mbexample.myapplication

object DataObject {
    private val dataList = mutableListOf<CardInfo>()

    @Synchronized
    fun setData(title: String, priority: String, date: String, time: String) {
        if (title.isBlank() || priority.isBlank()) {
            throw IllegalArgumentException("Title and priority must not be empty")
        }
        val cardInfo = CardInfo(title, priority, date, time)
        dataList.add(cardInfo)
    }

    @Synchronized
    fun getAllData(): List<CardInfo> = dataList.toList()

    @Synchronized
    fun deleteAll() {
        dataList.clear()
    }

    @Synchronized
    fun getData(pos: Int): CardInfo {
        if (pos < 0 || pos >= dataList.size) {
            throw IndexOutOfBoundsException("Invalid position: $pos")
        }
        return dataList[pos]
    }

    @Synchronized
    fun deleteData(pos: Int) {
        if (pos < 0 || pos >= dataList.size) {
            throw IndexOutOfBoundsException("Invalid position: $pos")
        }
        dataList.removeAt(pos)
    }

    @Synchronized
    fun updateData(pos: Int, title: String, priority: String, Rdate: String, Rtime: String) {
        if (pos < 0 || pos >= dataList.size) {
            throw IndexOutOfBoundsException("Invalid position: $pos")
        }
        dataList[pos].apply {
            this.title = title
            this.priority = priority
            this.Rdate = Rdate
            this.Rtime = Rtime
        }
    }
}
