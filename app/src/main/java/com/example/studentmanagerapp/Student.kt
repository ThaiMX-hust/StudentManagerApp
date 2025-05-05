import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Student(
    var name: String,
    var id: String,
    var email: String,
    var phone: String
) : Parcelable