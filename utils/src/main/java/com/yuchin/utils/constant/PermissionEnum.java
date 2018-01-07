package com.yuchin.utils.constant;


import android.Manifest;
import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

/**
 * Created by raphaelbussa on 22/06/16.
 */
@SuppressWarnings("SpellCheckingInspection")
@SuppressLint("InlinedApi")
public enum PermissionEnum {

    GROUP_CALENDAR(Manifest.permission_group.CALENDAR),
    READ_CALENDAR(Manifest.permission.READ_CALENDAR),
    WRITE_CALENDAR(Manifest.permission.WRITE_CALENDAR),

    GROUP_CAMERA(Manifest.permission_group.CAMERA),
    CAMERA(Manifest.permission.CAMERA),

    GROUP_CONTACTS(Manifest.permission_group.CONTACTS),
    READ_CONTACTS(Manifest.permission.READ_CONTACTS),
    WRITE_CONTACTS(Manifest.permission.WRITE_CONTACTS),
    GET_ACCOUNTS(Manifest.permission.GET_ACCOUNTS),

    GROUP_LOCATION(Manifest.permission_group.LOCATION),
    ACCESS_FINE_LOCATION(Manifest.permission.ACCESS_FINE_LOCATION),
    ACCESS_COARSE_LOCATION(Manifest.permission.ACCESS_COARSE_LOCATION),

    GROUP_MICROPHONE(Manifest.permission_group.MICROPHONE),
    RECORD_AUDIO(Manifest.permission.RECORD_AUDIO),

    GROUP_PHONE(Manifest.permission_group.PHONE),
    READ_PHONE_STATE(Manifest.permission.READ_PHONE_STATE),
    READ_PHONE_NUMBERS(Manifest.permission.READ_PHONE_NUMBERS),
    CALL_PHONE(Manifest.permission.CALL_PHONE),
    ANSWER_PHONE_CALLS(Manifest.permission.ANSWER_PHONE_CALLS),
    READ_CALL_LOG(Manifest.permission.READ_CALL_LOG),
    WRITE_CALL_LOG(Manifest.permission.WRITE_CALL_LOG),
    ADD_VOICEMAIL(Manifest.permission.ADD_VOICEMAIL),
    USE_SIP(Manifest.permission.USE_SIP),
    PROCESS_OUTGOING_CALLS(Manifest.permission.PROCESS_OUTGOING_CALLS),

    GROUP_SENSORS(Manifest.permission_group.SENSORS),
    BODY_SENSORS(Manifest.permission.BODY_SENSORS),

    GROUP_SMS(Manifest.permission_group.SMS),
    SEND_SMS(Manifest.permission.SEND_SMS),
    RECEIVE_SMS(Manifest.permission.RECEIVE_SMS),
    READ_SMS(Manifest.permission.READ_SMS),
    RECEIVE_WAP_PUSH(Manifest.permission.RECEIVE_WAP_PUSH),
    RECEIVE_MMS(Manifest.permission.RECEIVE_MMS),

    GROUP_STORAGE(Manifest.permission_group.STORAGE),
    READ_EXTERNAL_STORAGE(Manifest.permission.READ_EXTERNAL_STORAGE),
    WRITE_EXTERNAL_STORAGE(Manifest.permission.WRITE_EXTERNAL_STORAGE),

    NULL("");

    private final String permission;

    PermissionEnum(String permission) {
        this.permission = permission;
    }

    public static PermissionEnum fromManifestPermission(@NonNull String value) {
        for (PermissionEnum permissionEnum : PermissionEnum.values()) {
            if (value.equalsIgnoreCase(permissionEnum.permission)) {
                return permissionEnum;
            }
        }
        return NULL;
    }

    @Override
    public String toString() {
        return permission;
    }

}
