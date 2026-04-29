# Service层快速开始指南

## 目录
1. [项目结构](#项目结构)
2. [如何使用Service](#如何使用service)
3. [常见场景示例](#常见场景示例)
4. [注意事项](#注意事项)

## 项目结构

```
JPetStore_backend/
├── store-common/          # 公共模块（常量、异常、工具类）
├── store-model/           # 模型模块（Entity、DTO、VO、Enum）
│   └── src/main/java/com/csu/model/
│       ├── vo/            # VO类（小写包名）
│       ├── dto/           # DTO类
│       └── entity/        # Entity类
├── store-dao/             # 数据访问层（Mapper接口和XML）
├── store-service/         # 业务逻辑层（Service接口和实现）✨ 新构建
└── store-admin-web/       # Web层（Controller、视图）
```

## 如何使用Service

### 1. 在Controller中注入Service

```java
@Controller
@RequiredArgsConstructor  // Lombok自动生成构造函数
public class CategoryController {
    
    private final CategoryService categoryService;  // 自动注入
    
    @GetMapping("/admin/categories")
    public String list(Model model) {
        // 直接调用Service方法
        Page<CategoryVO> page = categoryService.getCategoryList(1, 10, null);
        model.addAttribute("categoryPage", page);
        return "admin/category/list";
    }
}
```

### 2. Service接口定义

```java
public interface CategoryService {
    Page<CategoryVO> getCategoryList(int pageNum, int pageSize, String keyword);
    CategoryVO getCategoryDetail(String categoryId);
    boolean addCategory(CategoryRequestDTO categoryDTO);
    boolean updateCategory(String categoryId, CategoryRequestDTO categoryDTO);
    boolean deleteCategory(String categoryId);
}
```

### 3. Service实现类

```java
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    
    private final CategoryMapper categoryMapper;  // 注入Mapper
    
    @Override
    public Page<CategoryVO> getCategoryList(int pageNum, int pageSize, String keyword) {
        // 1. 构建分页对象
        Page<Category> page = new Page<>(pageNum, pageSize);
        
        // 2. 构建查询条件
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            queryWrapper.like(Category::getName, keyword);
        }
        
        // 3. 执行查询
        Page<Category> categoryPage = categoryMapper.selectPage(page, queryWrapper);
        
        // 4. 转换为VO
        return convertToVOPage(categoryPage);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addCategory(CategoryRequestDTO categoryDTO) {
        // 1. 业务验证
        if (categoryMapper.countByCategoryId(categoryDTO.getCategoryId()) > 0) {
            throw new BusinessException("分类ID已存在");
        }
        
        // 2. DTO转Entity
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        
        // 3. 插入数据库
        return categoryMapper.insert(category) > 0;
    }
}
```

## 常见场景示例

### 场景1：分页查询列表

```java
@GetMapping
public String productList(@RequestParam(defaultValue = "1") int pageNum,
                         @RequestParam(defaultValue = "10") int pageSize,
                         @RequestParam(required = false) String keyword,
                         Model model) {
    // 调用Service获取分页数据
    Page<ProductVO> productPage = productService.getProductList(
        pageNum, pageSize, keyword, null
    );
    
    model.addAttribute("productPage", productPage);
    model.addAttribute("keyword", keyword);
    return "admin/product/list";
}
```

### 场景2：添加数据

```java
@PostMapping
@ResponseBody
public Result<Void> addProduct(@RequestBody ProductRequestDTO productDTO) {
    try {
        boolean success = productService.addProduct(productDTO);
        return success ? Result.success() : Result.error("添加失败");
    } catch (BusinessException e) {
        return Result.error(e.getMessage());
    }
}
```

### 场景3：更新数据

```java
@PutMapping("/{id}")
@ResponseBody
public Result<Void> updateItem(@PathVariable String id,
                               @RequestBody ItemRequestDTO itemDTO) {
    try {
        boolean success = itemService.updateItem(id, itemDTO);
        return success ? Result.success() : Result.error("更新失败");
    } catch (BusinessException e) {
        return Result.error(e.getMessage());
    }
}
```

### 场景4：删除数据

```java
@DeleteMapping("/{id}")
@ResponseBody
public Result<Void> deleteCategory(@PathVariable String id) {
    try {
        boolean success = categoryService.deleteCategory(id);
        return success ? Result.success() : Result.error("删除失败");
    } catch (BusinessException e) {
        return Result.error(e.getMessage());
    }
}
```

### 场景5：获取详情

```java
@GetMapping("/{orderId}")
public String orderDetail(@PathVariable int orderId, Model model) {
    // 获取订单详情（包含订单项和状态历史）
    OrderVO order = orderService.getOrderDetail(orderId);
    model.addAttribute("order", order);
    return "admin/order/detail";
}
```

### 场景6：事务操作

```java
@Override
@Transactional(rollbackFor = Exception.class)
public boolean addItem(ItemRequestDTO itemDTO) {
    // 1. 验证货品ID是否存在
    if (itemMapper.countByItemId(itemDTO.getItemId()) > 0) {
        throw new BusinessException("货品ID已存在");
    }
    
    // 2. 验证产品是否存在
    Product product = productMapper.selectById(itemDTO.getProductId());
    if (product == null) {
        throw new BusinessException("产品不存在");
    }
    
    // 3. 插入货品
    Item item = new Item();
    BeanUtils.copyProperties(itemDTO, item);
    boolean success = itemMapper.insert(item) > 0;
    
    // 4. 初始化库存（在同一事务中）
    if (success && itemDTO.getQuantity() != null) {
        ItemQuantity quantity = new ItemQuantity();
        quantity.setItemId(itemDTO.getItemId());
        quantity.setQuantity(itemDTO.getQuantity());
        itemQuantityMapper.insert(quantity);
    }
    
    return success;
    // 如果任何步骤抛出异常，整个事务会回滚
}
```

## 可用的Service列表

### 1. CategoryService - 分类管理
```java
categoryService.getCategoryList(pageNum, pageSize, keyword);
categoryService.getAllCategories();
categoryService.getCategoryDetail(categoryId);
categoryService.addCategory(categoryDTO);
categoryService.updateCategory(categoryId, categoryDTO);
categoryService.deleteCategory(categoryId);
```

### 2. ProductService - 产品管理
```java
productService.getProductList(pageNum, pageSize, keyword, categoryId);
productService.getProductsByCategory(categoryId);
productService.getProductDetail(productId);
productService.addProduct(productDTO);
productService.updateProduct(productId, productDTO);
productService.deleteProduct(productId);
```

### 3. ItemService - 货品管理
```java
itemService.getItemList(pageNum, pageSize, keyword, productId);
itemService.getItemDetail(itemId);
itemService.addItem(itemDTO);
itemService.updateItem(itemId, itemDTO);
itemService.deleteItem(itemId);
```

### 4. OrderService - 订单管理
```java
orderService.getOrderList(pageNum, pageSize, keyword, status);
orderService.getOrderDetail(orderId);
orderService.updateOrderStatus(statusDTO);
orderService.shipOrder(orderId, courier);
orderService.getTotalOrderCount();
orderService.getPendingOrderCount();
```

### 5. AccountService - 用户管理
```java
accountService.getUserList(pageNum, pageSize, keyword);
accountService.getUserDetail(username);
accountService.updateUserStatus(username, status);
accountService.deleteUser(username);
```

### 6. LoginService - 登录认证
```java
loginService.login(loginDTO);
loginService.validateUser(username);
```

## 注意事项

### ⚠️ 重要提示：VO类包名规范

**所有VO类都位于 `com.csu.model.vo` 包（小写）**

```java
// ✅ 正确的导入语句
import com.csu.model.vo.OrderVO;
import com.csu.model.vo.ItemVO;
import com.csu.model.vo.CategoryVO;

// ❌ 错误的导入语句（大写VO）
import com.csu.model.VO.OrderVO;  // 会导致编译错误
```

**原因说明：**
- VO类文件实际存储在 `store-model/src/main/java/com/csu/model/vo/` 目录
- Java的package声明必须与目录名完全一致（包括大小写）
- 之前使用大写 `VO` 导致类型不匹配错误，现已统一改为小写 `vo`

---

### ✅ 推荐做法

1. **使用构造函数注入**
   ```java
   @RequiredArgsConstructor
   public class XxxServiceImpl implements XxxService {
       private final XxxMapper xxxMapper;  // ✅ 好
   }
   ```

2. **写操作添加事务**
   ```java
   @Override
   @Transactional(rollbackFor = Exception.class)
   public boolean addXxx(XxxDTO dto) {
       // ✅ 确保数据一致性
   }
   ```

3. **进行业务验证**
   ```java
   if (xxxMapper.countById(id) > 0) {
       throw new BusinessException("ID已存在");  // ✅ 清晰的错误提示
   }
   ```

4. **统一返回VO**
   ```java
   private XxxVO convertToVO(Xxx entity) {
       XxxVO vo = new XxxVO();
       BeanUtils.copyProperties(entity, vo);
       return vo;  // ✅ 不直接返回Entity
   }
   ```

### ❌ 避免的做法

1. **不要在Service中使用字段存储状态**
   ```java
   @Service
   public class XxxServiceImpl {
       private int count;  // ❌ Service是单例的，会有线程安全问题
   }
   ```

2. **不要在循环中查询数据库**
   ```java
   // ❌ 性能差
   for (String id : ids) {
       mapper.selectById(id);
   }
   
   // ✅ 批量查询
   List<Xxx> list = mapper.selectBatchIds(ids);
   ```

3. **不要忘记事务回滚配置**
   ```java
   @Transactional(rollbackFor = Exception.class)  // ✅ 完整配置
   // 而不是
   @Transactional  // ❌ 只回滚RuntimeException
   ```

4. **不要直接返回Entity**
   ```java
   // ❌ 暴露数据库结构
   public Category getCategory(String id) {
       return categoryMapper.selectById(id);
   }
   
   // ✅ 返回VO
   public CategoryVO getCategory(String id) {
       Category category = categoryMapper.selectById(id);
       return convertToVO(category);
   }
   ```

## 调试技巧

### 1. 查看SQL日志
在 `application.yml` 中配置：
```yaml
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

### 2. 断点调试
在Service方法中设置断点，查看：
- 传入的参数
- 查询的结果
- 转换后的VO

### 3. 单元测试
```java
@SpringBootTest
class CategoryServiceTest {
    
    @Autowired
    private CategoryService categoryService;
    
    @Test
    void testGetCategoryList() {
        Page<CategoryVO> page = categoryService.getCategoryList(1, 10, null);
        assertNotNull(page);
        System.out.println("总数: " + page.getTotal());
    }
}
```

## 常见问题

### Q1: 为什么事务不生效？
**A:** 检查以下几点：
- 方法是否是 `public`
- 是否从外部调用（自调用不生效）
- 是否捕获了异常但没有抛出

### Q2: 如何测试Service？
**A:** 使用 `@SpringBootTest` + `@Autowired` 注入Service进行测试

### Q3: 如何处理复杂查询？
**A:** 
- 简单查询使用 MyBatis Plus 的 Wrapper
- 复杂查询在 Mapper XML 中编写自定义SQL

### Q4: 如何优化性能？
**A:**
- 添加索引
- 使用缓存（Redis）
- 批量操作
- 分页查询

## 下一步

1. ✅ Service层已构建完成
2. 📝 编写单元测试
3. 🔍 代码审查和优化
4. 🚀 部署测试
5. 📊 性能监控

## 相关文档

- [SERVICE_README.md](SERVICE_README.md) - Service层详细说明
- [SUMMARY.md](SUMMARY.md) - 构建总结

---

**祝开发顺利！** 🎉
