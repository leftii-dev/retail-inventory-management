package dev.austinbarnes.retailinventorymanagement.mapper;

import dev.austinbarnes.retailinventorymanagement.employee.mapper.EmployeeHierarchyMapper;
import dev.austinbarnes.retailinventorymanagement.employee.mapper.EmployeeMapper;
import dev.austinbarnes.retailinventorymanagement.employee.mapper.EmployeeRoleMapper;
import dev.austinbarnes.retailinventorymanagement.employee.mapper.RoleMapper;
import dev.austinbarnes.retailinventorymanagement.inventory.mapper.*;
import dev.austinbarnes.retailinventorymanagement.location.mapper.*;
import dev.austinbarnes.retailinventorymanagement.product.mapper.*;
import org.mapstruct.Mapper;

@Mapper(config = GlobalMapperConfig.class)
public interface GlobalMapper extends
        EmployeeHierarchyMapper,
        EmployeeMapper,
        EmployeeRoleMapper,
        RoleMapper,
        InventoryMapper,
        PurchaseOrderItemMapper,
        PurchaseOrderMapper,
        ReceivingVoucherItemMapper,
        ReceivingVoucherMapper,
        StatusMapper,
        TransferItemMapper,
        TransferMapper,
        VendorMapper,
        LocationDetailsMapper,
        LocationHoursMapper,
        LocationMapper,
        LocationTypeMapper,
        RetailLocationMapper,
        WarehouseLocationMapper,
        BrandMapper,
        CategoryHierarchyMapper,
        CategoryMapper,
        DiscountMapper,
        ProductMapper
{}
