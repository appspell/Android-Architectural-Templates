package com.appspell.android.templates.mvi.list.model

import android.os.Parcel
import android.os.Parcelable
import com.appspell.android.templates.mvi.list.model.entity.DataEntity

data class ListViewState(val loading: Boolean = false,
                         val list: List<DataEntity> = emptyList(),
                         val page: Int = 1,
                         val user: String = "",
                         val timestamp: Long = System.currentTimeMillis()) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readByte() != 0.toByte(),
            parcel.createTypedArrayList(DataEntity),
            parcel.readInt(),
            parcel.readString(),
            parcel.readLong()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (loading) 1 else 0)
        parcel.writeTypedList(list)
        parcel.writeInt(page)
        parcel.writeString(user)
        parcel.writeLong(timestamp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListViewState> {
        override fun createFromParcel(parcel: Parcel): ListViewState {
            return ListViewState(parcel)
        }

        override fun newArray(size: Int): Array<ListViewState?> {
            return arrayOfNulls(size)
        }
    }

}