package com.appspell.android.templates.mvi.list.model.entity

import android.os.Parcel
import android.os.Parcelable

data class DataEntity(
        val id: Long = -1,
        val name: String = "",
        val description: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataEntity> {
        override fun createFromParcel(parcel: Parcel): DataEntity {
            return DataEntity(parcel)
        }

        override fun newArray(size: Int): Array<DataEntity?> {
            return arrayOfNulls(size)
        }
    }
}
