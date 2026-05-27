# phân tích tại sao số lượng tồn kho không chính xác.
Không lưu thay đổi vào database
Code hiện tại:
product.setStockQuantity(newStock);
// productRepository.save(product);
Sau khi cập nhật tồn kho, hệ thống không gọi:
productRepository.save(product);
=> Dữ liệu chỉ thay đổi trên object trong bộ nhớ, không được lưu xuống database.
Hậu quả:
Tồn kho hiển thị sai
Không đồng bộ dữ liệu
Dễ xảy ra overselling

# Sai loại exception cho lỗi nghiệp vụ
Code hiện tại:
if (newStock < 0) {
throw new IllegalStateException(
"Resulting stock would be negative");
}
Theo nghiệp vụ:
Không cho phép tồn kho âm.
Đây là lỗi dữ liệu đầu vào không hợp lệ nên phù hợp hơn với:
IllegalArgumentException
Không nên dùng IllegalStateException.

# 2. Có bao nhiêu lỗi logic trong phương thức updateStock
Có 2 lỗi logic chính trong phương thức updateStock.
