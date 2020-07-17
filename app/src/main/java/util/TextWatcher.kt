package util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        var previousValue: String = ""
        override fun afterTextChanged(editable: Editable?) {
            if (previousValue != editable.toString())
                afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            previousValue = s.toString()
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}