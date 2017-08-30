package com.appspell.android.templates.mvi.list.model

import android.os.Parcel
import android.os.Parcelable

data class SimpleListViewState(val loading: Boolean = false,
                               val list: List<String> = emptyList()) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readByte() != 0.toByte(),
            parcel.createStringArrayList()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (loading) 1 else 0)
        parcel.writeStringList(list)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SimpleListViewState> {
        override fun createFromParcel(parcel: Parcel): SimpleListViewState {
            return SimpleListViewState(parcel)
        }

        override fun newArray(size: Int): Array<SimpleListViewState?> {
            return arrayOfNulls(size)
        }
    }

}