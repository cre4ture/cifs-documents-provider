package com.wa2c.android.cifsdocumentsprovider.data.storage.interfaces.utils

import android.provider.DocumentsContract
import android.webkit.MimeTypeMap
import com.wa2c.android.cifsdocumentsprovider.common.utils.appendSeparator
import com.wa2c.android.cifsdocumentsprovider.common.utils.getUriText
import com.wa2c.android.cifsdocumentsprovider.common.utils.mimeType
import com.wa2c.android.cifsdocumentsprovider.common.values.StorageType
import com.wa2c.android.cifsdocumentsprovider.common.values.UNC_SEPARATOR
import com.wa2c.android.cifsdocumentsprovider.common.values.UNC_START

/** Convert UNC Path (\\<server>\<share>\<path> to URI (smb://<server>/<share>/<path>) */
fun String.uncPathToUri(isDirectory: Boolean): String? {
    val elements = this.substringAfter(UNC_START).split(UNC_SEPARATOR).ifEmpty { return null }
    val params = elements.getOrNull(0)?.split('@') ?: return null
    val server = params.getOrNull(0) ?: return null
    val port = if (params.size >= 2) params.lastOrNull() else null
    val path = elements.subList(1, elements.size).joinToString(UNC_SEPARATOR)
    return getUriText(StorageType.SMBJ, server, port, path, isDirectory)
}

/**
 * Optimize URI
 */
fun String.optimizeUri(mimeType: String? = null): String {
    return  if (mimeType == null) {
        this
    } else if (mimeType == DocumentsContract.Document.MIME_TYPE_DIR) {
        this.appendSeparator()
    } else {
        val ext = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
        if (ext == this.mimeType) this
        else "$this.$ext"
    }
}
