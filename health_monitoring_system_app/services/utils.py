from flask import request, url_for
from flask_sqlalchemy.model import DefaultMeta
from flask_sqlalchemy.query import Query
from typing import Tuple

from config import Config

NON_FILTER_PARAMS = {'sort', 'page', 'per_page'}
PARAMS_SPLITTER = ','
REVERSE_SORTING_MARK = '-'


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
                validated_value = model.additional_validation(param, value)
                if validated_value is not None:
                    value = validated_value
                query_to_filter = query_to_filter.filter(column_attr == value)
    return query_to_filter


def apply_pagination(query_to_paginate: Query, function_name: str) -> Tuple[list, dict]:
    page = request.args.get('page', Config.DEFAULT_PAGE, type=int)
    per_page = request.args.get('per_page', Config.DEFAULT_PER_PAGE, type=int)
    params = {key: value for key, value in request.args.items() if key != 'page'}
    pagination_object = query_to_paginate.paginate(page=page, per_page=per_page, error_out=False)
    pagination = {
        'total_pages': pagination_object.pages,
        'total_records': pagination_object.total,
        'current_page': url_for(function_name, page=page, **params),
    }
    if pagination_object.has_next:
        pagination['next_page'] = url_for(function_name, page=page + 1, **params)

    if pagination_object.has_prev:
        pagination['previous_page'] = url_for(function_name, page=page - 1, **params)
    return pagination_object.items, pagination
