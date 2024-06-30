# import os

# # Ścieżka do katalogu
# directory = './normal'

# # Iteracja przez wszystkie pliki w katalogu
# for filename in os.listdir(directory):
#     # Sprawdzenie, czy plik zawiera "_mask" w nazwie
#     if '_mask' in filename:
#         file_path = os.path.join(directory, filename)
#         try:
#             # Usunięcie pliku
#             os.remove(file_path)
#             print(f'Usunięto plik: {file_path}')
#         except Exception as e:
#             print(f'Nie udało się usunąć pliku {file_path}. Błąd: {e}')


import os
import shutil
import random

def split_dataset(base_dir, categories, train_ratio=0.8, val_ratio=0.1, test_ratio=0.1):
    for category in categories:
        category_path = os.path.join(base_dir, category)
        files = [f for f in os.listdir(category_path) if os.path.isfile(os.path.join(category_path, f)) and '_mask' not in f]
        
        random.shuffle(files)
        
        train_size = int(len(files) * train_ratio)
        val_size = int(len(files) * val_ratio)
        test_size = len(files) - train_size - val_size
        
        train_files = files[:train_size]
        val_files = files[train_size:train_size + val_size]
        test_files = files[train_size + val_size:]
        
        for file_set, folder in [(train_files, 'train'), (val_files, 'validation'), (test_files, 'test')]:
            dest_dir = os.path.join(base_dir, folder, category)
            os.makedirs(dest_dir, exist_ok=True)
            for file in file_set:
                src_file = os.path.join(category_path, file)
                dest_file = os.path.join(dest_dir, file)
                shutil.copy(src_file, dest_file)
                print(f"Copied {src_file} to {dest_file}")

# Ścieżka do katalogu głównego
base_dir = '.'

# Kategorie (podkatalogi)
categories = ['benign', 'malignant', 'normal']

# Wywołanie funkcji
split_dataset(base_dir, categories)
