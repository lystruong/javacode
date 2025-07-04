package tools;

public interface Acceptable {

    // Định dạng biển số xe
    public final String LICENSE_PLATE_VALID = "^(5[0-9][A-Z][1-9]\\d{5})$"; // Biển số xe: 50-59 + ký tự quận + 5 chữ số
    // Tên chủ xe
    public final String OWNER_NAME_VALID = "^.{2,25}$"; // Tên chủ xe: không rỗng, từ 2 đến 25 ký tự
    // Số điện thoại
    public final String PHONE_VALID = "^(0\\d{9})$"; // Số điện thoại: 10 chữ số, bắt đầu bằng 0
    // Thương hiệu xe
    public final String CAR_BRAND_VALID = "^.{5,12}$"; // Thương hiệu xe: không rỗng, từ 5 đến 12 ký tự
    // Giá trị xe
    public final String VEHICLE_VALUE_VALID = "^(\\d{4,})$"; // Giá trị xe: số nguyên lớn hơn 999
    // Ngày đăng ký
    public final String REGISTRATION_DATE_VALID = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(\\d{4})$"; // Ngày đăng ký: định dạng dd/MM/yyyy    // Số ghế
    public final String NUMBER_OF_SEATS_VALID = "^(4|[5-9]|[1-2][0-9]|3[0-6])$"; // Số ghế: từ 4 đến 36

    public final String INSURANCE_ID_VALID = "^(\\d{4}$)";
    public final String INSURANCE_ESTABLISHED_DATE_VALID = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(\\d{4})$";
    public final String INSURANCE_PERIOD_VALID = "^(12|24|36)$";
     public final String INSURANCE_OWNER_VALID = "^.{2,25}$";
}
