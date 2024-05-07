from flask import request, url_for
from flask_sqlalchemy.model import DefaultMeta
from flask_sqlalchemy.query import Query
from typing import Tuple

NON_FILTER_PARAMS = {'sort', 'page', 'per_page'}
PARAMS_SPLITTER = ','
REVERSE_SORTING_MARK = '-'

DEFAULT_PAGE = 1
DEFAULT_PER_PAGE = 5


def apply_sort(model: DefaultMeta, query_to_sort: Query) -> Query:
    sort_keys = request.args.get('sort')
    if sort_keys:
        for key in sort_keys.split(PARAMS_SPLITTER):
            desc = False
            if key.startswith(REVERSE_SORTING_MARK):
                key = key[1:]
                desc = True
            column_attr = getattr(model, key, None)
            if column_attr is not None:
                query_to_sort = query_to_sort.order_by(column_attr.desc()) if desc else query_to_sort.order_by(
                    column_attr)
    return query_to_sort


def apply_filter(model: DefaultMeta, query_to_filter: Query) -> Query:
    for param, value in request.args.items():
        if param not in NON_FILTER_PARAMS:
            column_attr = getattr(model, param, None)
            if column_attr is not None:
                value = model.param_validation(param, value)
                if value is None:
                    continue
                query_to_filter = query_to_filter.filter(column_attr == value)
    return query_to_filter


# def apply_pagination(query_to_paginate: Query, function_name: str, path_param=None, path_value=None) -> Tuple[list, dict]:
#     page = request.args.get('page', DEFAULT_PAGE, type=int)
#     per_page = request.args.get('per_page', DEFAULT_PER_PAGE, type=int)
#     params = {key: value for key, value in request.args.items() if key != 'page'}
#     pagination_object = query_to_paginate.paginate(page=page, per_page=per_page, error_out=False)
#     pagination = {
#         'total_pages': pagination_object.pages,
#         'total_records': pagination_object.total,
#     }
#     if path_param is not None:
#         pagination['current_page'] = url_for(function_name, page=page, **{path_param: path_value}, **params)
#         if pagination_object.has_next:
#             pagination['next_page'] = url_for(function_name, page=page + 1, **{path_param: path_value}, **params)
#         if pagination_object.has_prev:
#             pagination['previous_page'] = url_for(function_name, page=page - 1, **{path_param: path_value}, **params)
#     else:
#         pagination['current_page'] = url_for(function_name, page=page, **params)
#         if pagination_object.has_next:
#             pagination['next_page'] = url_for(function_name, page=page + 1, **params)
#         if pagination_object.has_prev:
#             pagination['previous_page'] = url_for(function_name, page=page - 1, **params)
#
#     return pagination_object.items, pagination

def apply_pagination(query_to_paginate: Query, function_name: str, path_param=None, path_value=None) -> Tuple[
    list, dict]:
    page = request.args.get('page', DEFAULT_PAGE, type=int)
    per_page = request.args.get('per_page', DEFAULT_PER_PAGE, type=int)
    params = {key: value for key, value in request.args.items() if key != 'page'}
    pagination_object = query_to_paginate.paginate(page=page, per_page=per_page, error_out=False)
    pagination = {
        'total_pages': pagination_object.pages,
        'total_records': pagination_object.total,
    }

    if path_param is not None:
        current_page_kwargs = {path_param: path_value}
    else:
        current_page_kwargs = {}

    current_page_kwargs.update(params)
    pagination['current_page'] = build_url(function_name, page, current_page_kwargs)

    if pagination_object.has_next:
        next_page_kwargs = current_page_kwargs.copy()
        pagination['next_page'] = build_url(function_name, page + 1, next_page_kwargs)

    if pagination_object.has_prev:
        previous_page_kwargs = current_page_kwargs.copy()
        pagination['previous_page'] = build_url(function_name, page - 1, previous_page_kwargs)

    return pagination_object.items, pagination


def build_url(function_name: str, page: int, kwargs: dict) -> str:
    return url_for(function_name, page=page, **kwargs)
