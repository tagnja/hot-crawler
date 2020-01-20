# Database Design

- t_dict
- t_info_cate
- t_info_type
- t_info
- t_info_display
- t_user
- t_user_info
- t_user_passwd
- t_user_bind



### t_dict

| Name        | Type | Length | NULL | Key  | Description |
| ----------- | ---- | ------ | ---- | ---- | ----------- |
| id          |      |        |      |      |             |
| value       |      |        |      |      |             |
| parent_id   |      |        |      |      |             |
| sort_num    |      |        |      |      |             |
| is_delete   |      |        |      |      |             |
| create_time |      |        |      |      |             |
| modify_time |      |        |      |      |             |

### t_info_cate

| Name | Type | Length | NULL | Key  | Description |
| ---- | ---- | ------ | ---- | ---- | ----------- |
| id   |      |        |      |      |             |
| name |      |        |      |      |             |

### t_info_type

| Name    | Type    | Length | NULL     | Key  | Description |
| ------- | ------- | ------ | -------- | ---- | ----------- |
| id      | varchar | 64     | not null | P    |             |
| name    | enum    | 64     | not null |      |             |
| cate_id |         |        |          |      |             |

### t_info

| Name        | Type      | Length | NULL     | Key  | Description |
| ----------- | --------- | ------ | -------- | ---- | ----------- |
| id          | varchar   | 64     | not null | P    |             |
| title       | varchar   | 128    | not null |      |             |
| url         | varchar   | 255    | not null |      |             |
| url_hash    |           |        |          |      |             |
| info_type   |           |        |          | F    |             |
| create_time | timestamp |        |          |      |             |

index

- index(info_type)

### t_info_display

| Name         | Type | Length | NULL | Key  | Description |
| ------------ | ---- | ------ | ---- | ---- | ----------- |
| id           |      |        |      |      |             |
| info_id      |      |        |      |      |             |
| info_type    |      |        |      |      |             |
| display_time |      |        |      |      |             |
| sort_num     |      |        |      |      |             |

index

- index(display_time, info_type)
- //bitmap index(info_type)

---

t_user

| Name           | Type | Length | NULL | Key  | Description |
| -------------- | ---- | ------ | ---- | ---- | ----------- |
| id             |      |        |      | P    |             |
| username       |      |        |      |      |             |
| avatar_uri     |      |        |      |      |             |
| big_avatar_uri |      |        |      |      |             |
| language       |      |        |      |      |             |
| is_delete      |      |        |      |      |             |
| create_time    |      |        |      |      |             |
| modify_time    |      |        |      |      |             |

index

- index(username)

t_user_info

| Name        | Type | Length | NULL | Key  | Description |
| ----------- | ---- | ------ | ---- | ---- | ----------- |
| user_id     |      |        |      | P    |             |
| gender      |      |        |      |      |             |
| birth_date  |      |        |      |      |             |
| area_id     |      |        |      |      |             |
| address     |      |        |      |      |             |
| is_delete   |      |        |      |      |             |
| create_time |      |        |      |      |             |
| modify_time |      |        |      |      |             |

t_user_passwd

| Name        | Type | Length | NULL | Key  | Description |
| ----------- | ---- | ------ | ---- | ---- | ----------- |
| user_id     |      |        |      | P    |             |
| passwd      |      |        |      |      |             |
| is_delete   |      |        |      |      |             |
| create_time |      |        |      |      |             |
| modify_time |      |        |      |      |             |

t_user_bind

| Name         | Type           | Length | NULL | Key  | Description |
| ------------ | -------------- | ------ | ---- | ---- | ----------- |
| user_id      |                |        |      | P    |             |
| platform     | (email, phone) |        |      |      |             |
| open_id      |                |        |      |      |             |
| access_token |                |        |      |      |             |
| is_delete    |                |        |      |      |             |
| create_time  |                |        |      |      |             |
| modify_time  |                |        |      |      |             |



