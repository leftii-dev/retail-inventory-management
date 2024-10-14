package dev.austinbarnes.retailinventorymanagement.mapper;

import dev.austinbarnes.retailinventorymanagement.employee.mapper.EmployeeHierarchyMapper;
import dev.austinbarnes.retailinventorymanagement.employee.mapper.EmployeeMapper;
import dev.austinbarnes.retailinventorymanagement.employee.mapper.EmployeeRoleMapper;
import dev.austinbarnes.retailinventorymanagement.employee.mapper.RoleMapper;
import dev.austinbarnes.retailinventorymanagement.inventory.mapper.*;
import dev.austinbarnes.retailinventorymanagement.location.mapper.*;
import dev.austinbarnes.retailinventorymanagement.product.mapper.*;
import org.mapstruct.Mapper;

@Mapper(uses = {
        EmployeeHierarchyMapper.class,
        EmployeeMapper.class,
        EmployeeRoleMapper.class,
        RoleMapper.class,
        InventoryMapper.class,
        PurchaseOrderItemMapper.class,
        PurchaseOrderMapper.class,
        ReceivingVoucherItemMapper.class,
        ReceivingVoucherMapper.class,
        StatusMapper.class,
        TransferItemMapper.class,
        TransferMapper.class,
        VendorMapper.class,
        LocationDetailsMapper.class,
        LocationHoursMapper.class,
        LocationMapper.class,
        LocationTypeMapper.class,
        RetailLocationMapper.class,
        WarehouseLocationMapper.class,
        BrandMapper.class,
        CategoryHierarchyMapper.class,
        CategoryMapper.class,
        DiscountMapper.class,
        ProductMapper.class
})
public interface GlobalMapper {
}
