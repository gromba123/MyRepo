package com.example.myfootballcollection.data.firebase

import android.net.Uri
import android.os.Parcel
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseUserMetadata
import com.google.firebase.auth.MultiFactor
import com.google.firebase.auth.MultiFactorInfo
import com.google.firebase.auth.UserInfo
import com.google.firebase.auth.zzan

class FakeFirebaseUser() : FirebaseUser() {

    private lateinit var mail: String
        get
    private lateinit var password: String
    private lateinit var uuid: String
        get

    constructor(
        mail: String,
        password: String,
        uuid: String
    ) : this() {
        this.mail = mail
        this.password = password
        this.uuid = uuid
    }

    fun doesUserMatch(mail: String, password: String) = this.mail == mail && this.password == password

    fun changePassword(password: String) {
        this.password = password
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun getPhotoUrl(): Uri? {
        TODO("Not yet implemented")
    }

    override fun getDisplayName(): String? {
        TODO("Not yet implemented")
    }

    override fun getEmail(): String? {
        TODO("Not yet implemented")
    }

    override fun getPhoneNumber(): String? {
        TODO("Not yet implemented")
    }

    override fun getProviderId(): String {
        TODO("Not yet implemented")
    }

    override fun getUid(): String {
        TODO("Not yet implemented")
    }

    override fun isEmailVerified(): Boolean {
        TODO("Not yet implemented")
    }

    override fun zza(): FirebaseApp {
        TODO("Not yet implemented")
    }

    override fun zza(p0: MutableList<out UserInfo>): FirebaseUser {
        TODO("Not yet implemented")
    }

    override fun zza(p0: com.google.android.gms.internal.`firebase-auth-api`.zzagl) {
        TODO("Not yet implemented")
    }

    override fun zzb(): FirebaseUser {
        TODO("Not yet implemented")
    }

    override fun zzb(p0: MutableList<zzan>?) {
        TODO("Not yet implemented")
    }

    override fun getMetadata(): FirebaseUserMetadata? {
        TODO("Not yet implemented")
    }

    override fun getMultiFactor(): MultiFactor {
        TODO("Not yet implemented")
    }

    override fun zzc(): com.google.android.gms.internal.`firebase-auth-api`.zzagl {
        TODO("Not yet implemented")
    }

    override fun zzc(p0: MutableList<MultiFactorInfo>) {
        TODO("Not yet implemented")
    }

    override fun zzd(): String {
        TODO("Not yet implemented")
    }

    override fun zze(): String {
        TODO("Not yet implemented")
    }

    override fun getTenantId(): String? {
        TODO("Not yet implemented")
    }

    override fun zzf(): MutableList<zzan> {
        TODO("Not yet implemented")
    }

    override fun getProviderData(): MutableList<out UserInfo> {
        TODO("Not yet implemented")
    }

    override fun zzg(): MutableList<String>? {
        TODO("Not yet implemented")
    }

    override fun isAnonymous(): Boolean {
        TODO("Not yet implemented")
    }
}