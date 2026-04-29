# Service层说明

## 概述
Service层位于DAO层和Controller层之间，负责业务逻辑的处理。本项目的Service层采用了接口+实现类的设计模式。

## 目录结构
```
store-service/src/main/java/com/csu/service/
├── AccountService.java          # 账户服务接口
├── CategoryService.java         # 分类服务接口
├── ItemService.java             # 货品服务接口
├── LoginService.java            # 登录服务接口
├── OrderService.java            # 订单服务接口
├── ProductService.java          # 产品服务接口
└── impl/
    ├── AccountServiceImpl.java  # 账户服务实现
    ├── CategoryServiceImpl.java # 分类服务实现
    ├── ItemServiceImpl.java     # 货品服务实现
    ├── LoginServiceImpl.java    # 登录服务实现
    ├── OrderServiceImpl.java    # 订单服务实现
    └── ProductServiceImpl.java  # 产品服务实现
```

## 服务类说明

### 1. CategoryService（分类服务）
**功能：**
- 分页获取分类列表
- 获取所有分类
- 获取分类详情
- 添加、更新、删除分类

**主要方法：**
- `getCategoryList(int pageNum, int pageSize, String keyword)` - 分页查询
- `getAllCategories()` - 获取所有分类
- `addCategory(CategoryRequestDTO categoryDTO)` - 添加分类
- `updateCategory(String categoryId, CategoryRequestDTO categoryDTO)` - 更新分类
- `deleteCategory(String categoryId)` - 删除分类

### 2. ProductService（产品服务）
**功能：**
- 分页获取产品列表
- 根据分类获取产品列表
- 获取产品详情
- 添加、更新、删除产品

**主要方法：**
- `getProductList(int pageNum, int pageSize, String keyword, String categoryId)` - 分页查询
- `getProductsByCategory(String categoryId)` - 按分类查询
- `addProduct(ProductRequestDTO productDTO)` - 添加产品
- `updateProduct(String productId, ProductRequestDTO productDTO)` - 更新产品
- `deleteProduct(String productId)` - 删除产品

### 3. ItemService（货品服务）
**功能：**
- 分页获取货品列表（SKU）
- 获取货品详情
- 添加、更新、删除货品
- 管理货品库存

**主要方法：**
- `getItemList(int pageNum, int pageSize, String keyword, String productId)` - 分页查询
- `getItemDetail(String itemId)` - 获取详情
- `addItem(ItemRequestDTO itemDTO)` - 添加货品
- `updateItem(String itemId, ItemRequestDTO itemDTO)` - 更新货品
- `deleteItem(String itemId)` - 删除货品

### 4. OrderService（订单服务）
**功能：**
- 分页获取订单列表
- 获取订单详情（包含订单项和状态历史）
- 更新订单状态
- 发货处理
- 订单统计

**主要方法：**
- `getOrderList(int pageNum, int pageSize, String keyword, String status)` - 分页查询
- `getOrderDetail(int orderId)` - 获取订单详情
- `updateOrderStatus(OrderStatusUpdateDTO statusDTO)` - 更新状态
- `shipOrder(int orderId, String courier)` - 发货
- `getTotalOrderCount()` - 获取订单总数
- `getPendingOrderCount()` - 获取待处理订单数

### 5. AccountService（账户服务）
**功能：**
- 分页获取用户列表
- 获取用户详情（包含配置信息）
- 更新用户状态
- 删除用户

**主要方法：**
- `getUserList(int pageNum, int pageSize, String keyword)` - 分页查询
- `getUserDetail(String username)` - 获取用户详情
- `updateUserStatus(String username, String status)` - 更新状态
- `deleteUser(String username)` - 删除用户

### 6. LoginService（登录服务）
**功能：**
- 用户登录验证
- 验证用户状态

**主要方法：**
- `login(LoginDTO loginDTO)` - 用户登录
- `validateUser(String username)` - 验证用户

## 设计特点

### 1. 接口与实现分离
- 所有服务都定义了接口（Service Interface）
- 实现类放在impl包中（ServiceImpl）
- 便于单元测试和Mock

### 2. 事务管理
- 使用`@Transactional`注解管理事务
- 写操作（增删改）都添加了事务支持
- 设置`rollbackFor = Exception.class`确保异常时回滚

### 3. 异常处理
- 使用`BusinessException`抛出业务异常
- 在关键操作前进行数据验证
- 提供清晰的错误提示信息

### 4. 数据转换
- Entity → VO：用于返回给前端
- DTO → Entity：用于接收前端数据
- 每个Service都有对应的convertToVO方法

### 5. 依赖注入
- 使用`@RequiredArgsConstructor` + `final`字段
- Lombok自动生成构造函数
- 通过构造函数注入Mapper依赖

### 6. 分页支持
- 使用MyBatis Plus的Page对象
- 统一返回Page<VO>格式
- 支持关键字搜索和条件过滤

## 使用示例

### Controller中注入Service
```java
@Controller
@RequiredArgsConstructor
public class CategoryController {
    
    private final CategoryService categoryService;
    
    @GetMapping("/admin/categories")
    public String categoryList(@RequestParam(defaultValue = "1") int pageNum,
                              @RequestParam(defaultValue = "10") int pageSize,
                              Model model) {
        Page<CategoryVO> categoryPage = categoryService.getCategoryList(pageNum, pageSize, null);
        model.addAttribute("categoryPage", categoryPage);
        return "admin/category/list";
    }
}
```

### Service实现中的事务使用
```java
@Override
@Transactional(rollbackFor = Exception.class)
public boolean addCategory(CategoryRequestDTO categoryDTO) {
    // 业务验证
    if (categoryMapper.countByCategoryId(categoryDTO.getCategoryId()) > 0) {
        throw new BusinessException("分类ID已存在");
    }
    
    // 数据转换
    Category category = new Category();
    BeanUtils.copyProperties(categoryDTO, category);
    
    // 数据库操作
    return categoryMapper.insert(category) > 0;
}
```

## 注意事项

1. **线程安全**：Service类默认是单例的，不要使用实例变量存储状态
2. **事务边界**：事务应该在Service层控制，不要在Controller或DAO层开启事务
3. **异常处理**：Service层抛出业务异常，由Controller层统一处理
4. **日志记录**：建议在关键业务操作中添加日志记录
5. **性能优化**：避免在循环中进行数据库查询，尽量使用批量操作

## 扩展建议

1. 可以添加缓存支持（如Redis）提升查询性能
2. 可以添加异步处理用于耗时操作
3. 可以添加事件机制解耦业务逻辑
4. 可以添加AOP切面进行统一的日志记录和权限检查
