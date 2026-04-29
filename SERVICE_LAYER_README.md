# JPetStore Backend - Service层文档

## 📋 目录

- [项目简介](#项目简介)
- [Service层概述](#service层概述)
- [架构设计](#架构设计)
- [核心服务](#核心服务)
- [快速开始](#快速开始)
- [开发规范](#开发规范)
- [技术栈](#技术栈)

## 项目简介

JPetStore是一个宠物商店电商系统的后端项目，采用Spring Boot + MyBatis Plus构建。本项目采用了经典的分层架构：

```
Controller层 (store-admin-web)
    ↓
Service层 (store-service) ⭐ 本次构建完成
    ↓
DAO层 (store-dao)
    ↓
Database (MySQL)
```

## Service层概述

Service层是业务逻辑的核心，负责：
- ✅ 业务流程控制
- ✅ 事务管理
- ✅ 数据验证
- ✅ 数据转换（Entity ↔ VO/DTO）
- ✅ 业务规则实现

### Service层结构

```
store-service/
├── src/main/java/com/csu/service/
│   ├── AccountService.java          # 用户账户服务
│   ├── CategoryService.java         # 商品分类服务
│   ├── ItemService.java             # 货品SKU服务
│   ├── LoginService.java            # 登录认证服务
│   ├── OrderService.java            # 订单管理服务
│   ├── ProductService.java          # 商品产品服务
│   └── impl/
│       ├── AccountServiceImpl.java
│       ├── CategoryServiceImpl.java
│       ├── ItemServiceImpl.java
│       ├── LoginServiceImpl.java
│       ├── OrderServiceImpl.java
│       └── ProductServiceImpl.java
├── SERVICE_README.md                # 详细说明文档
├── QUICK_START.md                   # 快速开始指南
└── SUMMARY.md                       # 构建总结
```

## 架构设计

### 分层架构图

```
┌─────────────────────────────────────┐
│      Controller Layer               │  ← 接收HTTP请求
│   (store-admin-web)                 │
└──────────────┬──────────────────────┘
               │ 调用
┌──────────────▼──────────────────────┐
│      Service Layer ⭐               │  ← 业务逻辑处理
│   (store-service)                   │
│  • 事务管理                         │
│  • 业务验证                         │
│  • 数据转换                         │
└──────────────┬──────────────────────┘
               │ 调用
┌──────────────▼──────────────────────┐
│      DAO Layer                      │  ← 数据访问
│   (store-dao)                       │
│  • MyBatis Mapper                   │
│  • SQL映射                          │
└──────────────┬──────────────────────┘
               │ 查询
┌──────────────▼──────────────────────┐
│      Database                       │  ← 数据存储
│   (MySQL)                           │
└─────────────────────────────────────┘
```

### 设计模式

1. **接口与实现分离**
   ```java
   // 接口
   public interface CategoryService { ... }
   
   // 实现
   @Service
   public class CategoryServiceImpl implements CategoryService { ... }
   ```

2. **依赖注入**
   ```java
   @Service
   @RequiredArgsConstructor
   public class CategoryServiceImpl {
       private final CategoryMapper categoryMapper;  // 构造函数注入
   }
   ```

3. **数据传输对象**
   - DTO (Data Transfer Object): 接收前端参数
   - VO (View Object): 返回给前端
   - Entity: 数据库实体

## 核心服务

### 1. CategoryService - 商品分类管理

**功能：** 管理商品分类（如：狗、猫、鸟等）

**主要方法：**
```java
Page<CategoryVO> getCategoryList(int pageNum, int pageSize, String keyword);
CategoryVO getCategoryDetail(String categoryId);
boolean addCategory(CategoryRequestDTO categoryDTO);
boolean updateCategory(String categoryId, CategoryRequestDTO categoryDTO);
boolean deleteCategory(String categoryId);
```

**使用示例：**
```java
@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping("/categories")
    public String list(Model model) {
        Page<CategoryVO> page = categoryService.getCategoryList(1, 10, null);
        model.addAttribute("page", page);
        return "category/list";
    }
}
```

### 2. ProductService - 商品产品管理

**功能：** 管理产品（如：拉布拉多犬、波斯猫等）

**主要方法：**
```java
Page<ProductVO> getProductList(int pageNum, int pageSize, String keyword, String categoryId);
List<ProductVO> getProductsByCategory(String categoryId);
ProductVO getProductDetail(String productId);
boolean addProduct(ProductRequestDTO productDTO);
boolean updateProduct(String productId, ProductRequestDTO productDTO);
boolean deleteProduct(String productId);
```

### 3. ItemService - 货品SKU管理

**功能：** 管理具体货品（SKU），包含库存管理

**主要方法：**
```java
Page<ItemVO> getItemList(int pageNum, int pageSize, String keyword, String productId);
ItemVO getItemDetail(String itemId);
boolean addItem(ItemRequestDTO itemDTO);
boolean updateItem(String itemId, ItemRequestDTO itemDTO);
boolean deleteItem(String itemId);
```

**特点：**
- 自动管理库存（inventory表）
- 支持货品属性（颜色、尺寸等）
- 关联产品和分类信息

### 4. OrderService - 订单管理

**功能：** 管理订单全流程

**主要方法：**
```java
Page<OrderVO> getOrderList(int pageNum, int pageSize, String keyword, String status);
OrderVO getOrderDetail(int orderId);
boolean updateOrderStatus(OrderStatusUpdateDTO statusDTO);
boolean shipOrder(int orderId, String courier);
int getTotalOrderCount();
int getPendingOrderCount();
```

**特点：**
- 订单状态流转（待处理→已批准→已发货）
- 订单项明细
- 状态历史记录
- 订单统计

### 5. AccountService - 用户账户管理

**功能：** 管理用户账户和配置

**主要方法：**
```java
Page<AccountVO> getUserList(int pageNum, int pageSize, String keyword);
AccountVO getUserDetail(String username);
boolean updateUserStatus(String username, String status);
boolean deleteUser(String username);
```

**特点：**
- 用户基本信息
- 用户偏好设置
- 账户状态管理

### 6. LoginService - 登录认证

**功能：** 用户登录验证

**主要方法：**
```java
boolean login(LoginDTO loginDTO);
boolean validateUser(String username);
```

**特点：**
- 用户名密码验证
- 账户状态检查
- 安全的登录流程

## 快速开始

### 1. 在Controller中使用Service

```java
@Controller
@RequiredArgsConstructor
public class ProductController {
    
    private final ProductService productService;
    
    @GetMapping("/products")
    public String list(@RequestParam(defaultValue = "1") int pageNum,
                      Model model) {
        Page<ProductVO> page = productService.getProductList(pageNum, 10, null, null);
        model.addAttribute("productPage", page);
        return "product/list";
    }
}
```

### 2. 创建新的Service

**步骤1：** 定义接口
```java
public interface XxxService {
    XxxVO getDetail(String id);
    boolean add(XxxDTO dto);
}
```

**步骤2：** 实现类
```java
@Service
@RequiredArgsConstructor
public class XxxServiceImpl implements XxxService {
    
    private final XxxMapper xxxMapper;
    
    @Override
    public XxxVO getDetail(String id) {
        Xxx entity = xxxMapper.selectById(id);
        return convertToVO(entity);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(XxxDTO dto) {
        Xxx entity = new Xxx();
        BeanUtils.copyProperties(dto, entity);
        return xxxMapper.insert(entity) > 0;
    }
    
    private XxxVO convertToVO(Xxx entity) {
        XxxVO vo = new XxxVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
}
```

### 3. 编译运行

```bash
# 编译
mvn clean package -DskipTests

# 运行
java -jar store-admin-web/target/store-admin-web-1.0-SNAPSHOT.jar
```

## 开发规范

### 命名规范

- **Service接口：** `XxxService`
- **Service实现：** `XxxServiceImpl`
- **方法命名：**
  - 查询：`getXXX`, `listXXX`, `findXXX`
  - 添加：`addXXX`, `createXXX`
  - 更新：`updateXXX`, `modifyXXX`
  - 删除：`deleteXXX`, `removeXXX`

### 事务规范

```java
// ✅ 推荐：明确指定回滚异常
@Transactional(rollbackFor = Exception.class)
public boolean addXxx(XxxDTO dto) {
    // 写操作
}

// ❌ 避免：只回滚RuntimeException
@Transactional
public boolean addXxx(XxxDTO dto) {
    // 写操作
}
```

### 异常处理

```java
// Service层抛出业务异常
if (xxxMapper.countById(id) > 0) {
    throw new BusinessException("记录已存在");
}

// Controller层捕获并返回
try {
    service.addXxx(dto);
    return Result.success();
} catch (BusinessException e) {
    return Result.error(e.getMessage());
}
```

### 数据转换

```java
// Entity → VO（返回给前端）
private XxxVO convertToVO(Xxx entity) {
    XxxVO vo = new XxxVO();
    BeanUtils.copyProperties(entity, vo);
    // 额外处理
    return vo;
}

// DTO → Entity（接收前端数据）
Xxx entity = new Xxx();
BeanUtils.copyProperties(dto, entity);
```

## 技术栈

### 核心技术
- **Spring Boot** - 应用框架
- **MyBatis Plus** - ORM框架
- **Lombok** - 代码简化
- **Maven** - 项目管理

### 关键注解
- `@Service` - 标识Service组件
- `@Transactional` - 事务管理
- `@RequiredArgsConstructor` - 构造函数注入
- `@Autowired` - 依赖注入

### 工具类
- `BeanUtils` - 对象属性复制
- `StringUtils` - 字符串处理
- `LambdaQueryWrapper` - 条件查询

## 文档资源

- 📘 [SERVICE_README.md](store-service/SERVICE_README.md) - Service层详细说明
- 🚀 [QUICK_START.md](store-service/QUICK_START.md) - 快速开始指南
- 📊 [SUMMARY.md](store-service/SUMMARY.md) - 构建总结

## 模块依赖关系

```
store-admin-web (Web层)
    ↓ 依赖
store-service (Service层) ⭐
    ↓ 依赖
store-dao (DAO层)
    ↓ 依赖
store-model (模型层)
    ↓ 依赖
store-common (公共层)
```

## 性能优化建议

1. **缓存策略**
   - 对频繁查询的数据使用Redis缓存
   - 分类、产品等静态数据可以长期缓存

2. **数据库优化**
   - 为常用查询字段添加索引
   - 使用分页避免一次性加载大量数据
   - 批量操作减少数据库交互

3. **异步处理**
   - 耗时操作使用`@Async`异步执行
   - 发送邮件、短信等非关键操作异步化

4. **查询优化**
   - 避免N+1查询问题
   - 使用JOIN代替多次查询
   - 只查询需要的字段

## 测试建议

### 单元测试
```java
@SpringBootTest
class CategoryServiceTest {
    
    @Autowired
    private CategoryService categoryService;
    
    @Test
    void testGetCategory() {
        CategoryVO category = categoryService.getCategoryDetail("CATS");
        assertNotNull(category);
        assertEquals("Cats", category.getCategoryName());
    }
}
```

### Mock测试
```java
@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    
    @Mock
    private CategoryMapper categoryMapper;
    
    @InjectMocks
    private CategoryServiceImpl categoryService;
    
    @Test
    void testAddCategory() {
        when(categoryMapper.countByCategoryId(anyString())).thenReturn(0);
        when(categoryMapper.insert(any())).thenReturn(1);
        
        boolean result = categoryService.addCategory(dto);
        assertTrue(result);
    }
}
```

## 常见问题

### Q: 为什么事务不生效？
A: 确保：
1. 方法是public的
2. 从外部类调用（自调用不生效）
3. 添加了`@Transactional`注解
4. 异常被抛出而不是被捕获

### Q: 如何处理复杂查询？
A: 
- 简单查询使用MyBatis Plus的Wrapper
- 复杂查询在Mapper XML中编写自定义SQL
- 可以使用`@Select`注解编写动态SQL

### Q: Service层应该做什么？
A:
- ✅ 业务逻辑处理
- ✅ 事务控制
- ✅ 数据验证
- ✅ 数据转换
- ❌ 不应该直接处理HTTP请求
- ❌ 不应该包含视图逻辑

## 贡献指南

欢迎提交Issue和Pull Request来改进Service层！

## 许可证

本项目采用MIT许可证。

---

**开发团队** | 2026年4月
